package com.example.backend.exception;

public class ClaimNotExistException extends RuntimeException {
    public ClaimNotExistException(String message) {
        super(message);
    }
}
