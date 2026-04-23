package com.smartcampus.exception;

/**
 *
 * @author Thej
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}