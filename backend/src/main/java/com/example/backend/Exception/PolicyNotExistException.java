package com.example.backend.Exception;

public class PolicyNotExistException extends RuntimeException{
    public PolicyNotExistException(String message) {
        super(message);
    }
}
