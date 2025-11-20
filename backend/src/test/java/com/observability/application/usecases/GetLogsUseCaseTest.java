package com.observability.application.usecases;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.ports.output.LogRepository;
import com.observability.domain.valueobjects.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLogsUseCaseTest {
    
    @Mock
    private LogRepository logRepository;
    
    @InjectMocks
    private GetLogsUseCase getLogsUseCase;
    
    private List<LogEntry> mockLogs;
    
    @BeforeEach
    void setUp() {
        mockLogs = Arrays.asList(
            LogEntry.builder()
                .id(UUID.randomUUID().toString())
                .level(LogLevel.INFO)
                .message("Log 1")
                .service("service-1")
                .timestamp(Instant.now())
                .build(),
            LogEntry.builder()
                .id(UUID.randomUUID().toString())
                .level(LogLevel.ERROR)
                .message("Log 2")
                .service("service-2")
                .timestamp(Instant.now())
                .build()
        );
    }
    
    @Test
    void shouldRetrieveLogsWithFilters() {
        when(logRepository.findByFilters(
            any(), any(), any(), any(), any(), anyInt(), anyInt()
        )).thenReturn(mockLogs);
        
        List<LogEntry> result = getLogsUseCase.execute(
            LogLevel.INFO, "service-1", "host-1", 
            Instant.now().minusSeconds(3600), Instant.now(), 0, 20
        );
        
        assertEquals(2, result.size());
        verify(logRepository, times(1)).findByFilters(
            eq(LogLevel.INFO), eq("service-1"), eq("host-1"), 
            any(Instant.class), any(Instant.class), eq(0), eq(20)
        );
    }
    
    @Test
    void shouldUseDefaultTimeRangeWhenNotProvided() {
        when(logRepository.findByFilters(
            any(), any(), any(), any(), any(), anyInt(), anyInt()
        )).thenReturn(mockLogs);
        
        getLogsUseCase.execute(null, null, null, null, null, 0, 20);
        
        verify(logRepository, times(1)).findByFilters(
            any(), any(), any(), any(Instant.class), any(Instant.class), anyInt(), anyInt()
        );
    }
    
    @Test
    void shouldUseDefaultPaginationWhenInvalid() {
        when(logRepository.findByFilters(
            any(), any(), any(), any(), any(), anyInt(), anyInt()
        )).thenReturn(mockLogs);
        
        getLogsUseCase.execute(null, null, null, null, null, -1, 0);
        
        verify(logRepository, times(1)).findByFilters(
            any(), any(), any(), any(), any(), eq(0), eq(20)
        );
    }
    
    @Test
    void shouldLimitPageSizeTo100() {
        when(logRepository.findByFilters(
            any(), any(), any(), any(), any(), anyInt(), anyInt()
        )).thenReturn(mockLogs);
        
        getLogsUseCase.execute(null, null, null, null, null, 0, 200);
        
        verify(logRepository, times(1)).findByFilters(
            any(), any(), any(), any(), any(), eq(0), eq(20)
        );
    }
}

