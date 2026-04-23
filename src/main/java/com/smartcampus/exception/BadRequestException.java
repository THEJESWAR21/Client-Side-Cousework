package com.smartcampus.exception;

/**
 *
 * @author Thej
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
