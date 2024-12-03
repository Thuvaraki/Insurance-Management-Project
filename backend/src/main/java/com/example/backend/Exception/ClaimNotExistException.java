package com.example.backend.Exception;

public class ClaimNotExistException extends RuntimeException {
    public ClaimNotExistException(String message) {
        super(message);
    }
}
