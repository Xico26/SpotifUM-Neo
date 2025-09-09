package io.xico26.spotifum_neo.exceptions;

public class MusicAlreadySavedException extends RuntimeException {
    public MusicAlreadySavedException(String message) {
        super(message);
    }
}
