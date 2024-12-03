package com.example.backend.Exception;

public class PolicyAlreadyExistException extends RuntimeException{
    public PolicyAlreadyExistException(String message) {
        super(message);
    }
}
