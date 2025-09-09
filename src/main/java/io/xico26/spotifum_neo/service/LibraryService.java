package io.xico26.spotifum_neo.service;

import io.xico26.spotifum_neo.dao.LibraryDAO;
import io.xico26.spotifum_neo.entity.Album;
import io.xico26.spotifum_neo.entity.Library;
import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.entity.playlist.Playlist;
import io.xico26.spotifum_neo.exceptions.AlbumAlreadySavedException;
import io.xico26.spotifum_neo.exceptions.MusicAlreadySavedException;
import io.xico26.spotifum_neo.exceptions.PlaylistAlreadySavedException;

import java.util.List;

public class LibraryService {
    private final LibraryDAO libraryDAO;

    public LibraryService(LibraryDAO libraryDAO) {
        this.libraryDAO = libraryDAO;
    }

    public Library getUserLibrary (User u) {
        return libraryDAO.findByUser(u);
    }

    public boolean hasMusic (User u, Music m) {
        Library library = getUserLibrary(u);
        return library.getMusics().contains(m);
    }

    public void addMusic (User u, Music m) {
        Library library = getUserLibrary(u);
        if (!library.getMusics().contains(m)) {
            library.addMusic(m);
            libraryDAO.update(library);
        } else {
            throw new MusicAlreadySavedException("Music already saved!");
        }
    }

    public void removeMusic (User u, Music m) {
        Library library = getUserLibrary(u);
        if (library.getMusics().contains(m)) {
            library.removeMusic(m);
            libraryDAO.update(library);
        }
    }

    public void addAlbum(User user, Album album) {
        Library library = libraryDAO.findByUser(user);
        if (!library.getAlbums().contains(album)) {
            library.addAlbum(album);
            libraryDAO.update(library);
        } else {
            throw new AlbumAlreadySavedException("Album already saved!");
        }
    }

    public void removeAlbum(User user, Album album) {
        Library library = libraryDAO.findByUser(user);
        if (library.getAlbums().contains(album)) {
            library.removeAlbum(album);
            libraryDAO.update(library);
        }
    }

    public boolean hasAlbum(User u, Album album) {
        Library library = getUserLibrary(u);
        return library.getAlbums().contains(album);
    }

    public void addPlaylist(User user, Playlist playlist) {
        Library library = libraryDAO.findByUser(user);
        if (!library.getPlaylists().contains(playlist)) {
            library.addPlaylist(playlist);
            libraryDAO.update(library);
        } else {
            throw new PlaylistAlreadySavedException("Playlist already saved!");
        }
    }

    public void removePlaylist(User user, Playlist playlist) {
        Library library = libraryDAO.findByUser(user);
        if (library.getPlaylists().contains(playlist)) {
            library.removePlaylist(playlist);
            libraryDAO.update(library);
        }
    }

    public boolean hasPlaylist(User u, Playlist playlist) {
        Library library = getUserLibrary(u);
        return library.getPlaylists().contains(playlist);
    }

    public boolean hasPlaylistByName (User u, String name) {
        Library library = getUserLibrary(u);
        return library.getPlaylists().stream().anyMatch(p -> p.getName().equals(name));
    }

    public void removePlaylistByName (User u, String name) {
        Library library = getUserLibrary(u);
        if (hasPlaylistByName(u, name)) {
            library.removePlaylist(library.getPlaylists().stream().filter(p -> p.getName().equals(name)).findFirst().get());
            libraryDAO.update(library);
        }
    }

    public List<Library> findAllWithMusic (Music music) {
        return libraryDAO.findAllWithMusic(music);
    }

    public List<Library> findAllWithPlaylist (Playlist playlist) {
        return libraryDAO.findAllWithPlaylist(playlist);
    }

    public void save (Library library) {
        libraryDAO.save(library);
    }
}
