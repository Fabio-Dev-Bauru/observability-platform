package com.observability.application.usecases;

import com.observability.domain.entities.Alert;
import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.ports.output.EventPublisher;
import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResolveAlertUseCaseTest {
    
    @Mock
    private AlertRepository alertRepository;
    
    @Mock
    private EventPublisher eventPublisher;
    
    @InjectMocks
    private ResolveAlertUseCase resolveAlertUseCase;
    
    private Alert activeAlert;
    
    @BeforeEach
    void setUp() {
        activeAlert = Alert.builder()
            .id(UUID.randomUUID().toString())
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
    }
    
    @Test
    void shouldResolveAlertSuccessfully() {
        when(alertRepository.findById(activeAlert.getId())).thenReturn(Optional.of(activeAlert));
        when(alertRepository.save(any(Alert.class))).thenReturn(activeAlert);
        
        Alert result = resolveAlertUseCase.execute(activeAlert.getId());
        
        assertEquals(AlertStatus.RESOLVED, result.getStatus());
        assertNotNull(result.getResolvedAt());
        verify(alertRepository, times(1)).findById(activeAlert.getId());
        verify(alertRepository, times(1)).save(any(Alert.class));
        verify(eventPublisher, times(1)).publishAlertEvent(any(Alert.class));
    }
    
    @Test
    void shouldThrowExceptionWhenAlertNotFound() {
        String nonExistentId = UUID.randomUUID().toString();
        when(alertRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            resolveAlertUseCase.execute(nonExistentId);
        });
        
        verify(alertRepository, never()).save(any());
        verify(eventPublisher, never()).publishAlertEvent(any());
    }
}

