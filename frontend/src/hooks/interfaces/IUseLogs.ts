import { LogEntry } from '@/types/log'

export interface IUseLogs {
  logs: LogEntry[]
  loading: boolean
  error: string | null
  loadLogs: () => Promise<void>
}

