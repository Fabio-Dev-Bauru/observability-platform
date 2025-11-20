package com.observability.adapters.input.websocket;

import com.observability.domain.entities.Metric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MetricWebSocketController {
    
    @MessageMapping("/metrics")
    @SendTo("/topic/metrics")
    public Metric broadcastMetric(Metric metric) {
        log.debug("Broadcasting metric via WebSocket: {}", metric.getId());
        return metric;
    }
}

