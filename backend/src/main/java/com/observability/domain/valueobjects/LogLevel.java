package com.observability.domain.valueobjects;

/**
 * Represents the severity level of a log entry.
 * 
 * This value object ensures type safety and provides
 * a clear enumeration of valid log levels.
 */
public enum LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    FATAL
}

