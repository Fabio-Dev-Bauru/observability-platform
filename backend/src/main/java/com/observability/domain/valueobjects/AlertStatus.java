package com.observability.domain.valueobjects;

/**
 * Represents the current status of an alert.
 */
public enum AlertStatus {
    ACTIVE,
    RESOLVED,
    ACKNOWLEDGED,
    SUPPRESSED
}

