export enum LogLevel {
  TRACE = 'TRACE',
  DEBUG = 'DEBUG',
  INFO = 'INFO',
  WARN = 'WARN',
  ERROR = 'ERROR',
  FATAL = 'FATAL',
}

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

export interface LogFilters {
  level?: LogLevel
  service?: string
  host?: string
  startTime?: string
  endTime?: string
  page?: number
  size?: number
}

