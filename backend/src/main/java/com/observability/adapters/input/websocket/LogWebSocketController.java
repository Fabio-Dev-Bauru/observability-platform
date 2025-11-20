package com.observability.adapters.input.websocket;

import com.observability.domain.entities.LogEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class LogWebSocketController {
    
    @MessageMapping("/logs")
    @SendTo("/topic/logs")
    public LogEntry broadcastLog(LogEntry logEntry) {
        log.debug("Broadcasting log entry via WebSocket: {}", logEntry.getId());
        return logEntry;
    }
}

