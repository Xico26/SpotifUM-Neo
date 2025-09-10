package io.xico26.spotifum_neo.music;

import io.xico26.spotifum_neo.album.AlbumService;
import io.xico26.spotifum_neo.album.Album;
import io.xico26.spotifum_neo.library.Library;
import io.xico26.spotifum_neo.playlist.Playlist;
import io.xico26.spotifum_neo.exceptions.AlbumNotFoundException;
import io.xico26.spotifum_neo.exceptions.NameAlreadyUsedException;
import io.xico26.spotifum_neo.library.LibraryService;
import io.xico26.spotifum_neo.playlist.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {
    private final MusicDAO musicDAO;
    private AlbumService albumService;
    private LibraryService libraryService;
    private PlaylistService playlistService;

    @Autowired
    public MusicService(MusicDAO musicDAO, AlbumService albumService) {
        this.musicDAO = musicDAO;
        this.albumService = albumService;
    }

    public void setAlbumService (AlbumService albumService) {
        this.albumService = albumService;
    }

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public Music findById(int id) {
        return musicDAO.findById(id);
    }

    public List<Music> findAll() {
        return musicDAO.findAll();
    }

    @Transactional
    public void save(Music music) {
        musicDAO.save(music);
    }

    @Transactional
    public void delete(Music music) {
        // Remove from all playlists
        List<Playlist> playlists = playlistService.findAllWithMusic(music);
        for (Playlist p : playlists) {
            p.getMusics().remove(music);
            playlistService.save(p);
        }

        // Remove from all libraries
        List<Library> libraries = libraryService.findAllWithMusic(music);
        for (Library l : libraries) {
            l.getMusics().remove(music);
            libraryService.save(l);
        }

        // Remove from album
        if (music.getAlbum() != null) {
            Album album = music.getAlbum();
            album.getMusics().remove(music);
            albumService.save(album);
        }

        // Delete music
        musicDAO.deleteById(music.getId());
    }

    public int getTotalNumberOfMusics() {
        return musicDAO.getMusicCount();
    }

    public List<Music> searchByTitle(String title) {
        return musicDAO.findByTitle(title);
    }

    public List<Music> searchByGenre(String genre) {
        return musicDAO.getMusicsByGenre(genre);
    }

    public List<Music> searchByArtist(String artist) {
        List<Music> musics = new ArrayList<Music>();
        albumService.searchByArtist(artist).forEach(a -> musics.addAll(a.getMusics()));

        return musics;
    }

    @Transactional
    public void makeExplicit (Music music) {
        ExplicitMusic newMusic = new ExplicitMusic(music);

        // delete old
        delete(music);

        // save new
        save(newMusic);

        // add to album
        Album album = music.getAlbum();
        album.getMusics().add(newMusic);
        albumService.save(album);
    }

    @Transactional
    public void makeNormal (ExplicitMusic music) {
        Music newMusic = new Music(music);

        // delete old
        delete(music);

        // save new
        save(newMusic);

        // add to album
        Album album = music.getAlbum();
        album.getMusics().add(newMusic);
        albumService.save(album);
    }

    public void createMusic(int albumId, String name, String genre, int duration, List<String> lyrics) {
        Album album = albumService.findById(albumId);

        if (album == null) {
            throw new AlbumNotFoundException("Album not found!");
        }

        if (album.getMusics().stream().anyMatch(m -> m.getTitle().equals(name))) {
            throw new NameAlreadyUsedException("Music with this name already exists!");
        }

        // process lyrics
        StringBuilder sb = new StringBuilder();
        for (String lyric : lyrics) {
            sb.append(lyric).append('\n');
        }

        String lyricsString =  sb.toString();

        Music newMusic = new Music(name, genre, lyricsString, duration, album);

        save(newMusic);

        albumService.addMusic(album.getId(), newMusic);
    }
}

