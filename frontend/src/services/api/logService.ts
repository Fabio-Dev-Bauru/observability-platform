import axios from 'axios'
import { LogEntry, LogEntryRequest, LogFilters } from '@/types/log'

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'

const apiClient = axios.create({
  baseURL: `${API_URL}/api/v1`,
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * Service for log-related API operations
 */
export const logService = {
  /**
   * Creates a new log entry
   * 
   * @param logEntry - The log entry to create
   * @returns The created log entry
   */
  async createLog(logEntry: LogEntryRequest): Promise<LogEntry> {
    const response = await apiClient.post<LogEntry>('/logs', logEntry)
    return response.data
  },

  /**
   * Retrieves log entries with optional filters
   * 
   * @param filters - Optional filters for querying logs
   * @returns Array of log entries
   */
  async getLogs(filters?: LogFilters): Promise<LogEntry[]> {
    const params = new URLSearchParams()
    
    if (filters?.level) params.append('level', filters.level)
    if (filters?.service) params.append('service', filters.service)
    if (filters?.host) params.append('host', filters.host)
    if (filters?.startTime) params.append('startTime', filters.startTime)
    if (filters?.endTime) params.append('endTime', filters.endTime)
    if (filters?.page !== undefined) params.append('page', filters.page.toString())
    if (filters?.size !== undefined) params.append('size', filters.size.toString())

    const response = await apiClient.get<LogEntry[]>(`/logs?${params.toString()}`)
    return response.data
  },
}

