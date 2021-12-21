package com.hs1.exceptions;

public class EncryptionException extends RuntimeException {
    public EncryptionException(final String message, final Exception e) {
        super(message, e);
        System.out.println(message);
    }
}
