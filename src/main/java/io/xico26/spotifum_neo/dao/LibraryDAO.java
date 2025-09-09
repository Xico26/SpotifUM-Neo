package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.Library;
import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.entity.playlist.Playlist;

import java.util.List;

public interface LibraryDAO {
    Library findByUser (User u);

    void save (Library library);

    void update (Library library);

    List<Library> findAllWithMusic(Music music);

    List<Library> findAllWithPlaylist(Playlist playlist);
}
