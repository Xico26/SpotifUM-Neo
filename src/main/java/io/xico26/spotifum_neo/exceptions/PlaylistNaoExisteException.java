package io.xico26.spotifum_neo.exceptions;

public class PlaylistNaoExisteException extends RuntimeException {
    public PlaylistNaoExisteException(String message) {
        super(message);
    }
}
