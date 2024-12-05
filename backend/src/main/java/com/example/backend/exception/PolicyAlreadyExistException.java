package com.example.backend.exception;

public class PolicyAlreadyExistException extends RuntimeException{
    public PolicyAlreadyExistException(String message) {
        super(message);
    }
}
