import { LogEntry } from '@/types/log'
import { IUseQuery } from './IUseQuery'

export interface IUseLogs extends Omit<IUseQuery<LogEntry[]>, 'data' | 'refetch'> {
  logs: LogEntry[]
  loadLogs: () => Promise<void>
}

