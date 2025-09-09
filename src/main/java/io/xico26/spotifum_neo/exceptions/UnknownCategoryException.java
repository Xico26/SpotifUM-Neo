package io.xico26.spotifum_neo.exceptions;

public class UnknownCategoryException extends RuntimeException {
    public UnknownCategoryException(String message) {
        super(message);
    }
}
