package com.hs1.exceptions;

public class EncryptionException extends RuntimeException {
    public EncryptionException(String message, Exception e) {
        super(message, e);
    }
}
