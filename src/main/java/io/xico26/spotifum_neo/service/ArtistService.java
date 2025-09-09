package io.xico26.spotifum_neo.service;

import io.xico26.spotifum_neo.dao.ArtistDAO;
import io.xico26.spotifum_neo.entity.Album;
import io.xico26.spotifum_neo.entity.Artist;

import java.util.List;

public class ArtistService {
    private final ArtistDAO artistDAO;

    public ArtistService(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public Artist findById (int id) {
        return artistDAO.findById(id);
    }

    public Artist findByName (String name) {
        return artistDAO.findByName(name);
    }

    public List<Artist> findAll() {
        return artistDAO.findAll();
    }

    public void save(Artist artist) {
        artistDAO.save(artist);
    }

    public void delete(Artist artist) {
        artistDAO.delete(artist);
    }

    public void update(Artist artist) {
        artistDAO.update(artist);
    }

    public void addAlbum(Artist artist, Album album) {
        artistDAO.addAlbum(artist, album);
    }
}
