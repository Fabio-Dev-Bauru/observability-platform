package com.observability.application.dtos;

import com.observability.domain.valueobjects.AlertSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequest {
    
    @NotBlank(message = "Alert name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Alert severity is required")
    private AlertSeverity severity;
    
    private String service;
    
    private Map<String, Object> metadata;
    
    private String ruleId;
}

