package io.xico26.spotifum_neo.playlist;

import io.xico26.spotifum_neo.album.AlbumService;
import io.xico26.spotifum_neo.album.Album;
import io.xico26.spotifum_neo.library.Library;
import io.xico26.spotifum_neo.library.LibraryService;
import io.xico26.spotifum_neo.music.MusicService;
import io.xico26.spotifum_neo.listeningrecord.ListeningRecordService;
import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;
import io.xico26.spotifum_neo.exceptions.NameAlreadyUsedException;
import io.xico26.spotifum_neo.exceptions.TooFewMusicsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PlaylistService {
    private final PlaylistDAO playlistDAO;
    private final LibraryService libraryService;
    private final ListeningRecordService listeningRecordService;
    private final MusicService musicService;
    private final AlbumService albumService;
    private static final Random random = new Random();

    @Autowired
    public PlaylistService(PlaylistDAO playlistDAO, LibraryService libraryService, ListeningRecordService listeningRecordService, MusicService musicService, AlbumService albumService) {
        this.playlistDAO = playlistDAO;
        this.libraryService = libraryService;
        this.listeningRecordService = listeningRecordService;
        this.musicService = musicService;
        this.albumService = albumService;
    }

    public Playlist findById(int id) {
        return playlistDAO.findById(id);
    }

    public List<Playlist> findByUser(User user) {
        return playlistDAO.findByUser(user);
    }

    public List<Playlist> findPublicPlaylists () {
        return playlistDAO.findPublicPlaylists();
    }

    public List<Playlist> findAll() {
        return playlistDAO.findAll();
    }

    @Transactional
    public void save(Playlist playlist) {
        playlistDAO.save(playlist);
    }

    @Transactional
    public void delete(Playlist playlist) {
        // remove from libraries
        List<Library> libraries = libraryService.findAllWithPlaylist(playlist);
        for (Library library : libraries) {
            library.removePlaylist(playlist);
            libraryService.save(library);
        }

        playlistDAO.deleteById(playlist.getId());
    }

    @Transactional
    public void update(Playlist playlist) {
        playlistDAO.update(playlist);
    }

    public List<Playlist> searchByTitle(String title) {
        return playlistDAO.findByTitle(title);
    }

    public List<Playlist> findAllWithMusic (Music music) {
        return playlistDAO.findAllWithMusic(music);
    }

    public void generateFavouritesList(User user, int numMusics) throws TooFewMusicsException {
        String name = "Favourites List";
        if (listeningRecordService.getNumListened(user) < 10){
            throw new TooFewMusicsException("You need to listen to at least 10 musics to be able to generate a Favourites List!");
        }

        if (libraryService.hasPlaylistByName(user, name)) {
            libraryService.removePlaylistByName(user, name);
        }

        List<Music> uniqueMusics = listeningRecordService.getUniqueListens(user);
        Map<Music,Integer> weights = new HashMap<Music,Integer>();

        for (Music music : uniqueMusics) {
            int weight = listeningRecordService.getNumListensToMusic(user, music);
            weights.put(music, weight);
        }

        FavouriteList favouriteList = new FavouriteList(name, user);

        weights.entrySet().stream()
                .sorted(Map.Entry.<Music, Integer>comparingByValue().reversed())
                .limit(numMusics)
                .forEach(entry -> favouriteList.addMusic(entry.getKey()));

        save(favouriteList);
        libraryService.addPlaylist(user, favouriteList);
    }

    public void generateGenreList(String name, String genre, User u, int numMusics) throws NameAlreadyUsedException, TooFewMusicsException {
        if (libraryService.hasPlaylistByName(u, name)) {
            throw new NameAlreadyUsedException("There's already a playlist with the name: " + name);
        }

        if (musicService.getTotalNumberOfMusics() == 0) {
            throw new TooFewMusicsException("There aren't enough musics in the database!");
        }

        GenreList genreList = new GenreList(name, u);

        List<Music> genreMusics = musicService.searchByGenre(genre);
        genreMusics.stream().limit(numMusics).forEach(genreList::addMusic);

        save(genreList);
        libraryService.addPlaylist(u, genreList);
    }

    public boolean hasMusic(Playlist playlist, Music music) {
        if (playlist.getMusics() == null || playlist.getMusics().isEmpty()) {
            return false;
        }

        return playlist.getMusics().stream().anyMatch(m -> m.equals(music));
    }

    public void toggleVisibility(Playlist playlist) {
        playlist.setIsPublic(!playlist.isPublic());
        save(playlist);
    }

    public RandomPlaylist generateRandomPlaylist(String name, int numMusics, User user) throws TooFewMusicsException {
        RandomPlaylist randomPlaylist = new RandomPlaylist(name, user);
        List<Album> albums = albumService.findAll();

        int totalMusics = musicService.getTotalNumberOfMusics();

        if (totalMusics == 0 || albums.isEmpty()) {
            throw new TooFewMusicsException("There aren't enough musics in the database!");
        }
        if (numMusics > totalMusics) {
            numMusics = totalMusics;
        }

        while (randomPlaylist.getMusics().size() < numMusics) {
            int r1 = random.nextInt(albums.size());
            Album album = albums.get(r1);
            List<Music> ms = album.getMusics();
            if (ms.isEmpty()) {
                continue;
            }
            int r2 = random.nextInt(ms.size());
            Music music = ms.get(r2);
            if (!randomPlaylist.getMusics().contains(music)) {
                randomPlaylist.addMusic(music);
            }
        }

        return randomPlaylist;
    }

    public void createPlaylist (String name, User user) {
        if (libraryService.getUserLibrary(user).getPlaylists().stream().anyMatch(p -> p.getName().equals(name))) {
            throw new NameAlreadyUsedException("There's already a playlist with this name!");
        }
        Playlist newPlaylist = new CustomPlaylist(name, user);
        libraryService.addPlaylist(user, newPlaylist);

        save(newPlaylist);
    }
}
