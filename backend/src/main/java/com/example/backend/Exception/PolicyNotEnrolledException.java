package com.example.backend.Exception;

public class PolicyNotEnrolledException extends RuntimeException {
    public PolicyNotEnrolledException(String message) {
        super(message);
    }
}
