package com.example.backend.exception;

public class PolicyNotEnrolledException extends RuntimeException {
    public PolicyNotEnrolledException(String message) {
        super(message);
    }
}
