package io.xico26.spotifum_neo.exceptions;

public class MusicNotFoundException extends RuntimeException {
    public MusicNotFoundException(String message) {
        super(message);
    }
}
