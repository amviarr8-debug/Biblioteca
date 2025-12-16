package com.example.biblioteca.exception;

// Es una RuntimeException simple
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
