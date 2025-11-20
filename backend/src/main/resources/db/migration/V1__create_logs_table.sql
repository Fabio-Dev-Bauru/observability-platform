-- Create logs table
CREATE TABLE IF NOT EXISTS logs (
    id VARCHAR(255) PRIMARY KEY,
    level VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    service VARCHAR(255) NOT NULL,
    host VARCHAR(255),
    timestamp TIMESTAMP NOT NULL,
    tags JSONB,
    trace_id VARCHAR(255),
    span_id VARCHAR(255),
    metadata JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for common queries
CREATE INDEX IF NOT EXISTS idx_logs_timestamp ON logs(timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_logs_level ON logs(level);
CREATE INDEX IF NOT EXISTS idx_logs_service ON logs(service);
CREATE INDEX IF NOT EXISTS idx_logs_host ON logs(host);
CREATE INDEX IF NOT EXISTS idx_logs_trace_id ON logs(trace_id);

-- Create hypertable for time-series data (TimescaleDB)
SELECT create_hypertable('logs', 'timestamp', if_not_exists => TRUE);

