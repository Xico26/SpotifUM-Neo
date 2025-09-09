package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.entity.playlist.Playlist;

import java.util.List;

public interface PlaylistDAO {
    Playlist findById(int id);

    List<Playlist> findAll();

    void save(Playlist playlist);

    void update(Playlist playlist);

    void delete(Playlist playlist);

    List<Playlist> findByUser(User user);

    List<Playlist> findPublicPlaylists();

    List<Playlist> findByTitle(String title);

    List<Playlist> findAllWithMusic(Music music);

}
