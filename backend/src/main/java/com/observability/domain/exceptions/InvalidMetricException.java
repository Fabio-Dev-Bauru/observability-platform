package com.observability.domain.exceptions;

public class InvalidMetricException extends RuntimeException {
    
    public InvalidMetricException(String message) {
        super(message);
    }
    
    public InvalidMetricException(String message, Throwable cause) {
        super(message, cause);
    }
}

