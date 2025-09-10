package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.Album;
import io.xico26.spotifum_neo.entity.Artist;

import java.util.List;

public interface ArtistDAO {
    Artist findById(int id);

    List<Artist> findAll();

    Artist findByName(String name);

    void save(Artist a);

    void deleteById(int id);

    void update(Artist a);

    void addAlbum (Artist artist, Album album);
}
