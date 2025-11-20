package com.observability.infrastructure.prometheus;

import com.observability.application.usecases.ImportPrometheusMetricsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrometheusScraper {
    
    private final ImportPrometheusMetricsUseCase importPrometheusMetricsUseCase;
    private final RestTemplate restTemplate;
    
    @Value("${prometheus.scrape.enabled:false}")
    private boolean scrapeEnabled;
    
    @Value("${prometheus.scrape.url:}")
    private String prometheusUrl;
    
    @Value("${prometheus.scrape.interval:60000}")
    private long scrapeInterval;
    
    @Scheduled(fixedDelayString = "${prometheus.scrape.interval:60000}")
    public void scrapePrometheusMetrics() {
        if (!scrapeEnabled || prometheusUrl == null || prometheusUrl.isEmpty()) {
            return;
        }
        
        try {
            log.debug("Scraping metrics from Prometheus: {}", prometheusUrl);
            String metricsData = restTemplate.getForObject(prometheusUrl + "/api/v1/query?query=up", String.class);
            
            if (metricsData != null) {
                importPrometheusMetricsUseCase.execute(metricsData);
                log.debug("Successfully scraped metrics from Prometheus");
            }
        } catch (Exception e) {
            log.warn("Failed to scrape Prometheus metrics: {}", e.getMessage());
        }
    }
}

