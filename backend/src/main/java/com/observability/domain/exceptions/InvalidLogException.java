package com.observability.domain.exceptions;

/**
 * Exception thrown when a log entry is invalid or malformed.
 */
public class InvalidLogException extends RuntimeException {
    
    public InvalidLogException(String message) {
        super(message);
    }
    
    public InvalidLogException(String message, Throwable cause) {
        super(message, cause);
    }
}

