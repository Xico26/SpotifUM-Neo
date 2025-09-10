package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.Album;

import java.util.List;

public interface AlbumDAO {
    Album findById(int id);

    List<Album> findAll();

    void save(Album a);

    void deleteById(int id);

    void update(Album a);

    List<Album> findByTitle(String title);

    List<Album> findByArtist(String artist);
}
