package com.observability.infrastructure.prometheus;

import com.observability.domain.entities.Metric;
import com.observability.domain.entities.PrometheusMetric;
import com.observability.domain.valueobjects.MetricType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PrometheusParser {
    
    private static final Pattern METRIC_PATTERN = Pattern.compile(
        "^([a-zA-Z_:][a-zA-Z0-9_:]*)\\s*" +
        "\\{([^}]*)\\}\\s*" +
        "([+-]?[0-9]*\\.?[0-9]+(?:[eE][+-]?[0-9]+)?)\\s*" +
        "(?:([0-9]+))?$"
    );
    
    private static final Pattern LABEL_PATTERN = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)=\"([^\"]*)\"");
    
    public List<Metric> parsePrometheusFormat(String prometheusData) {
        List<Metric> metrics = new ArrayList<>();
        String[] lines = prometheusData.split("\n");
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            
            PrometheusMetric promMetric = parseLine(line);
            if (promMetric != null) {
                Metric metric = convertToMetric(promMetric);
                if (metric != null) {
                    metrics.add(metric);
                }
            }
        }
        
        return metrics;
    }
    
    private PrometheusMetric parseLine(String line) {
        Matcher matcher = METRIC_PATTERN.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        
        String name = matcher.group(1);
        String labelsStr = matcher.group(2);
        String valueStr = matcher.group(3);
        String timestampStr = matcher.group(4);
        
        try {
            Double value = Double.parseDouble(valueStr);
            Map<String, String> labels = parseLabels(labelsStr);
            Long timestamp = timestampStr != null ? Long.parseLong(timestampStr) : System.currentTimeMillis() / 1000;
            
            return PrometheusMetric.builder()
                .name(name)
                .value(value)
                .labels(labels)
                .timestamp(timestamp)
                .build();
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private Map<String, String> parseLabels(String labelsStr) {
        Map<String, String> labels = new HashMap<>();
        if (labelsStr == null || labelsStr.isEmpty()) {
            return labels;
        }
        
        Matcher matcher = LABEL_PATTERN.matcher(labelsStr);
        while (matcher.find()) {
            labels.put(matcher.group(1), matcher.group(2));
        }
        
        return labels;
    }
    
    private Metric convertToMetric(PrometheusMetric promMetric) {
        MetricType type = inferMetricType(promMetric.getName());
        String service = promMetric.getLabels().getOrDefault("service", promMetric.getLabels().get("job"));
        String host = promMetric.getLabels().get("instance");
        
        Instant timestamp = promMetric.getTimestamp() != null
            ? Instant.ofEpochSecond(promMetric.getTimestamp())
            : Instant.now();
        
        Map<String, String> labels = new HashMap<>(promMetric.getLabels());
        labels.remove("service");
        labels.remove("job");
        labels.remove("instance");
        
        return Metric.builder()
            .name(promMetric.getName())
            .value(promMetric.getValue())
            .type(type)
            .service(service)
            .host(host)
            .timestamp(timestamp)
            .labels(labels.isEmpty() ? null : labels)
            .build();
    }
    
    private MetricType inferMetricType(String metricName) {
        String lowerName = metricName.toLowerCase();
        if (lowerName.contains("_total") || lowerName.contains("_count")) {
            return MetricType.COUNTER;
        } else if (lowerName.contains("_bucket")) {
            return MetricType.HISTOGRAM;
        } else if (lowerName.contains("_sum") || lowerName.contains("quantile")) {
            return MetricType.SUMMARY;
        } else {
            return MetricType.GAUGE;
        }
    }
}

