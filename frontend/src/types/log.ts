/**
 * Log level enumeration
 */
export enum LogLevel {
  TRACE = 'TRACE',
  DEBUG = 'DEBUG',
  INFO = 'INFO',
  WARN = 'WARN',
  ERROR = 'ERROR',
  FATAL = 'FATAL',
}

/**
 * Log entry interface
 */
export interface LogEntry {
  id: string
  level: LogLevel
  message: string
  service: string
  host?: string
  timestamp: string
  tags?: Record<string, string>
  traceId?: string
  spanId?: string
  metadata?: Record<string, unknown>
}

/**
 * Log entry request for creating new logs
 */
export interface LogEntryRequest {
  level: LogLevel
  message: string
  service: string
  host?: string
  timestamp?: string
  tags?: Record<string, string>
  traceId?: string
  spanId?: string
  metadata?: Record<string, unknown>
}

/**
 * Log filters for querying logs
 */
export interface LogFilters {
  level?: LogLevel
  service?: string
  host?: string
  startTime?: string
  endTime?: string
  page?: number
  size?: number
}

