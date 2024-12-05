package com.example.backend.exception;

public class PolicyNotExistException extends RuntimeException{
    public PolicyNotExistException(String message) {
        super(message);
    }
}
