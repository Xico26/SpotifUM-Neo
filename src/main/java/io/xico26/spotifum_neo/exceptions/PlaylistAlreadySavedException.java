package io.xico26.spotifum_neo.exceptions;

public class PlaylistAlreadySavedException extends RuntimeException {
    public PlaylistAlreadySavedException(String message) {
        super(message);
    }
}
