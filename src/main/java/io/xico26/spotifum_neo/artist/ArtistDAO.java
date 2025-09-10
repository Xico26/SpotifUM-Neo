package io.xico26.spotifum_neo.artist;

import io.xico26.spotifum_neo.album.Album;

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
