package com.observability.application.usecases;

import com.observability.domain.entities.Metric;
import com.observability.domain.exceptions.InvalidMetricException;
import com.observability.domain.ports.output.EventPublisher;
import com.observability.domain.ports.output.MetricRepository;
import com.observability.domain.valueobjects.MetricType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMetricUseCaseTest {
    
    @Mock
    private MetricRepository metricRepository;
    
    @Mock
    private EventPublisher eventPublisher;
    
    @InjectMocks
    private CreateMetricUseCase createMetricUseCase;
    
    private Metric validMetric;
    
    @BeforeEach
    void setUp() {
        validMetric = Metric.builder()
            .name("cpu.usage")
            .value(75.5)
            .type(MetricType.GAUGE)
            .timestamp(Instant.now())
            .build();
    }
    
    @Test
    void shouldCreateMetricSuccessfully() {
        Metric savedMetric = Metric.builder()
            .id(UUID.randomUUID().toString())
            .name(validMetric.getName())
            .value(validMetric.getValue())
            .type(validMetric.getType())
            .timestamp(validMetric.getTimestamp())
            .build();
        
        when(metricRepository.save(any(Metric.class))).thenReturn(savedMetric);
        
        Metric result = createMetricUseCase.execute(validMetric);
        
        assertNotNull(result);
        assertNotNull(result.getId());
        verify(metricRepository, times(1)).save(any(Metric.class));
        verify(eventPublisher, times(1)).publishMetricEvent(any(Metric.class));
    }
    
    @Test
    void shouldThrowExceptionWhenMetricIsInvalid() {
        Metric invalidMetric = Metric.builder()
            .name("")
            .value(75.5)
            .type(MetricType.GAUGE)
            .timestamp(Instant.now())
            .build();
        
        assertThrows(InvalidMetricException.class, () -> {
            createMetricUseCase.execute(invalidMetric);
        });
        
        verify(metricRepository, never()).save(any());
        verify(eventPublisher, never()).publishMetricEvent(any());
    }
}

