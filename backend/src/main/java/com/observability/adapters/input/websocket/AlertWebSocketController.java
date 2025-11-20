package com.observability.adapters.input.websocket;

import com.observability.domain.entities.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AlertWebSocketController {
    
    @MessageMapping("/alerts")
    @SendTo("/topic/alerts")
    public Alert broadcastAlert(Alert alert) {
        log.debug("Broadcasting alert via WebSocket: {}", alert.getId());
        return alert;
    }
}

