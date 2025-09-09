package io.xico26.spotifum_neo.exceptions;

public class AlbumAlreadySavedException extends RuntimeException {
    public AlbumAlreadySavedException(String message) {
        super(message);
    }
}
