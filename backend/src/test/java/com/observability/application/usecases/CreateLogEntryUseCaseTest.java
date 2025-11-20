package com.observability.application.usecases;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.exceptions.InvalidLogException;
import com.observability.domain.ports.output.EventPublisher;
import com.observability.domain.ports.output.LogRepository;
import com.observability.domain.valueobjects.LogLevel;
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
class CreateLogEntryUseCaseTest {
    
    @Mock
    private LogRepository logRepository;
    
    @Mock
    private EventPublisher eventPublisher;
    
    @InjectMocks
    private CreateLogEntryUseCase createLogEntryUseCase;
    
    private LogEntry validLogEntry;
    
    @BeforeEach
    void setUp() {
        validLogEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .service("test-service")
            .timestamp(Instant.now())
            .build();
    }
    
    @Test
    void shouldCreateLogEntrySuccessfully() {
        LogEntry savedLogEntry = LogEntry.builder()
            .id(UUID.randomUUID().toString())
            .level(validLogEntry.getLevel())
            .message(validLogEntry.getMessage())
            .service(validLogEntry.getService())
            .timestamp(validLogEntry.getTimestamp())
            .build();
        
        when(logRepository.save(any(LogEntry.class))).thenReturn(savedLogEntry);
        
        LogEntry result = createLogEntryUseCase.execute(validLogEntry);
        
        assertNotNull(result);
        assertNotNull(result.getId());
        verify(logRepository, times(1)).save(any(LogEntry.class));
        verify(eventPublisher, times(1)).publishLogEvent(any(LogEntry.class));
    }
    
    @Test
    void shouldGenerateIdWhenNotProvided() {
        LogEntry savedLogEntry = LogEntry.builder()
            .id(UUID.randomUUID().toString())
            .level(validLogEntry.getLevel())
            .message(validLogEntry.getMessage())
            .service(validLogEntry.getService())
            .timestamp(validLogEntry.getTimestamp())
            .build();
        
        when(logRepository.save(any(LogEntry.class))).thenReturn(savedLogEntry);
        
        LogEntry result = createLogEntryUseCase.execute(validLogEntry);
        
        assertNotNull(result.getId());
        verify(logRepository, times(1)).save(any(LogEntry.class));
    }
    
    @Test
    void shouldThrowExceptionWhenLogEntryIsInvalid() {
        LogEntry invalidLogEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("")
            .service("test-service")
            .timestamp(Instant.now())
            .build();
        
        assertThrows(InvalidLogException.class, () -> {
            createLogEntryUseCase.execute(invalidLogEntry);
        });
        
        verify(logRepository, never()).save(any());
        verify(eventPublisher, never()).publishLogEvent(any());
    }
    
    @Test
    void shouldPreserveProvidedId() {
        String providedId = UUID.randomUUID().toString();
        LogEntry logEntryWithId = LogEntry.builder()
            .id(providedId)
            .level(validLogEntry.getLevel())
            .message(validLogEntry.getMessage())
            .service(validLogEntry.getService())
            .timestamp(validLogEntry.getTimestamp())
            .build();
        
        when(logRepository.save(any(LogEntry.class))).thenReturn(logEntryWithId);
        
        LogEntry result = createLogEntryUseCase.execute(logEntryWithId);
        
        assertEquals(providedId, result.getId());
    }
}

