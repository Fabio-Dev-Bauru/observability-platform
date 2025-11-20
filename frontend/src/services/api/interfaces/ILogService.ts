import { LogEntry, LogEntryRequest, LogFilters } from '@/types/log'

export interface ILogService {
  createLog(logEntry: LogEntryRequest): Promise<LogEntry>
  getLogs(filters?: LogFilters): Promise<LogEntry[]>
}

