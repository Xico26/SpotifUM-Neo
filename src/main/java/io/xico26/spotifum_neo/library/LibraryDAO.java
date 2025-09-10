package io.xico26.spotifum_neo.library;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;
import io.xico26.spotifum_neo.playlist.Playlist;

import java.util.List;

public interface LibraryDAO {
    Library findByUser (User u);

    void save (Library library);

    void update (Library library);

    List<Library> findAllWithMusic(Music music);

    List<Library> findAllWithPlaylist(Playlist playlist);
}
