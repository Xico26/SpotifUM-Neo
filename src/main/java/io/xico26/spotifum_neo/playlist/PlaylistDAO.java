package io.xico26.spotifum_neo.playlist;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;

import java.util.List;

public interface PlaylistDAO {
    Playlist findById(int id);

    List<Playlist> findAll();

    void save(Playlist playlist);

    void update(Playlist playlist);

    void deleteById(int id);

    List<Playlist> findByUser(User user);

    List<Playlist> findPublicPlaylists();

    List<Playlist> findByTitle(String title);

    List<Playlist> findAllWithMusic(Music music);

}
