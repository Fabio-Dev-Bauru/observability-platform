package com.observability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Observability Center.
 * 
 * This application provides a full-stack observability platform
 * with hexagonal architecture for collecting and visualizing
 * logs, metrics, and events in real-time.
 */
@SpringBootApplication
public class ObservabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservabilityApplication.class, args);
    }
}

