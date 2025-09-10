package io.xico26.spotifum_neo.artist;

import io.xico26.spotifum_neo.album.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistDAO artistDAO;

    @Autowired
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

    @Transactional
    public void save(Artist artist) {
        artistDAO.save(artist);
    }

    @Transactional
    public void delete(Artist artist) {
        artistDAO.deleteById(artist.getId());
    }

    @Transactional
    public void update(Artist artist) {
        artistDAO.update(artist);
    }

    @Transactional
    public void addAlbum(Artist artist, Album album) {
        artistDAO.addAlbum(artist, album);
    }
}
