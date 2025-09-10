package io.xico26.spotifum_neo.music;

import java.util.List;

public interface MusicDAO {
    Music findById(int id);

    List<Music> findAll();

    void save(Music music);

    void deleteById(int id);

    int getMusicCount();

    List<Music> findByTitle(String title);

    List<Music> getMusicsByGenre(String genre);
}

