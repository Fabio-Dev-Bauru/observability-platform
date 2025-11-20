-- Create metrics table
CREATE TABLE IF NOT EXISTS metrics (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    value DOUBLE PRECISION NOT NULL,
    service VARCHAR(255),
    host VARCHAR(255),
    timestamp TIMESTAMP NOT NULL,
    labels JSONB,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for common queries
CREATE INDEX IF NOT EXISTS idx_metrics_timestamp ON metrics(timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_metrics_name ON metrics(name);
CREATE INDEX IF NOT EXISTS idx_metrics_service ON metrics(service);
CREATE INDEX IF NOT EXISTS idx_metrics_host ON metrics(host);

-- Create hypertable for time-series data (TimescaleDB)
SELECT create_hypertable('metrics', 'timestamp', if_not_exists => TRUE);

