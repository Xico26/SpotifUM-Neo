package io.xico26.spotifum_neo.service;

import io.xico26.spotifum_neo.dao.AlbumDAO;
import io.xico26.spotifum_neo.entity.Album;
import io.xico26.spotifum_neo.entity.Artist;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.exceptions.AlbumNotFoundException;
import io.xico26.spotifum_neo.exceptions.MusicAlreadySavedException;

import java.util.List;

public class AlbumService {
    private final AlbumDAO albumDAO;
    private final ArtistService artistService;

    public AlbumService(AlbumDAO albumDAO, ArtistService artistService) {
        this.albumDAO = albumDAO;
        this.artistService = artistService;
    }

    public Album findById(int id) {
        return albumDAO.findById(id);
    }

    public List<Album> findAll() {
        return albumDAO.findAll();
    }

    public List<Album> searchByTitle(String title) {
        return albumDAO.findByTitle(title);
    }

    public List<Album> searchByArtist(String artist) {
        return albumDAO.findByArtist(artist);
    }

    public void save (Album album) {
        albumDAO.save(album);
    }

    public void delete (Album album) {
        albumDAO.delete(album);
    }

    public boolean hasMusic(int albumId, int musicId) throws AlbumNotFoundException {
        Album album = albumDAO.findById(albumId);
        if (album == null) {
            throw new AlbumNotFoundException("Album with id " + albumId + " not found!");
        }

        if (album.getMusics() == null) {
            return false;
        }

        return album.getMusics().stream().anyMatch(m -> m.getId() == musicId);
    }

    public void addMusic (int albumId, Music m) {
        Album album = albumDAO.findById(albumId);
        if (album == null) {
            throw new AlbumNotFoundException("Album with id " + albumId + " not found!");
        }

        if (album.getMusics().contains(m)) {
            throw new MusicAlreadySavedException("Music with id " + m.getId() + " already exists in the album!");
        }

        album.getMusics().add(m);
        albumDAO.update(album);
    }

    public void removeMusic (int albumId, int musicId) {
        if (!hasMusic(albumId, musicId)) {
            return;
        }

        Album album = albumDAO.findById(albumId);
        album.getMusics().removeIf(m -> m.getId() == musicId);
        albumDAO.update(album);
    }

    public void createAlbum(String title, String artistName, String label, int year) {
        Artist artist = artistService.findByName(artistName);
        Album album = new Album(title, label, year, artist);

        save(album);
        artistService.addAlbum(artist, album);
    }
}
