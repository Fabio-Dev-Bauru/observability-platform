import { ILogService } from '../interfaces/ILogService'
import { IApiClient } from '../interfaces/IApiClient'
import { LogEntry, LogEntryRequest, LogFilters } from '@/types/log'
import { QueryParamsBuilder } from '../utils/QueryParamsBuilder'

export class LogService implements ILogService {
  constructor(private apiClient: IApiClient) {}

  async createLog(logEntry: LogEntryRequest): Promise<LogEntry> {
    return this.apiClient.post<LogEntry>('/logs', logEntry)
  }

  async getLogs(filters?: LogFilters): Promise<LogEntry[]> {
    const params = QueryParamsBuilder.fromObject({
      level: filters?.level,
      service: filters?.service,
      host: filters?.host,
      startTime: filters?.startTime,
      endTime: filters?.endTime,
      page: filters?.page,
      size: filters?.size,
    })

    const url = params ? `/logs?${params}` : '/logs'
    return this.apiClient.get<LogEntry[]>(url)
  }
}

