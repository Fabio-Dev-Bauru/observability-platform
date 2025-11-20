package com.observability.domain.exceptions;

/**
 * Exception thrown when a metric is invalid or malformed.
 */
public class InvalidMetricException extends RuntimeException {
    
    public InvalidMetricException(String message) {
        super(message);
    }
    
    public InvalidMetricException(String message, Throwable cause) {
        super(message, cause);
    }
}

