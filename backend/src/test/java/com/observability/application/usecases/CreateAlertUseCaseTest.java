package com.observability.application.usecases;

import com.observability.domain.entities.Alert;
import com.observability.domain.exceptions.InvalidAlertException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAlertUseCaseTest {
    
    @Mock
    private AlertRepository alertRepository;
    
    @Mock
    private EventPublisher eventPublisher;
    
    @InjectMocks
    private CreateAlertUseCase createAlertUseCase;
    
    private Alert validAlert;
    
    @BeforeEach
    void setUp() {
        validAlert = Alert.builder()
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
    }
    
    @Test
    void shouldCreateAlertSuccessfully() {
        Alert savedAlert = Alert.builder()
            .id(UUID.randomUUID().toString())
            .name(validAlert.getName())
            .severity(validAlert.getSeverity())
            .status(validAlert.getStatus())
            .triggeredAt(validAlert.getTriggeredAt())
            .build();
        
        when(alertRepository.save(any(Alert.class))).thenReturn(savedAlert);
        
        Alert result = createAlertUseCase.execute(validAlert);
        
        assertNotNull(result);
        assertNotNull(result.getId());
        verify(alertRepository, times(1)).save(any(Alert.class));
        verify(eventPublisher, times(1)).publishAlertEvent(any(Alert.class));
    }
    
    @Test
    void shouldThrowExceptionWhenAlertIsInvalid() {
        Alert invalidAlert = Alert.builder()
            .name("")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
        
        assertThrows(InvalidAlertException.class, () -> {
            createAlertUseCase.execute(invalidAlert);
        });
        
        verify(alertRepository, never()).save(any());
        verify(eventPublisher, never()).publishAlertEvent(any());
    }
}

