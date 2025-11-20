package com.observability.domain.exceptions;

public class InvalidAlertException extends RuntimeException {
    
    public InvalidAlertException(String message) {
        super(message);
    }
    
    public InvalidAlertException(String message, Throwable cause) {
        super(message, cause);
    }
}

