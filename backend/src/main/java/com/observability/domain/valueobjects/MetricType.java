package com.observability.domain.valueobjects;

/**
 * Represents the type of a metric.
 * 
 * Different metric types have different semantics:
 * - COUNTER: Monotonically increasing value
 * - GAUGE: Value that can go up or down
 * - HISTOGRAM: Distribution of measurements
 * - SUMMARY: Summary statistics
 */
public enum MetricType {
    COUNTER,
    GAUGE,
    HISTOGRAM,
    SUMMARY
}

