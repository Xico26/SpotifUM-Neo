package io.xico26.spotifum_neo.exceptions;

public class NameAlreadyUsedException extends RuntimeException {
    public NameAlreadyUsedException(String message) {
        super(message);
    }
}
