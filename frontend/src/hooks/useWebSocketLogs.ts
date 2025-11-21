import { useQueryClient } from '@tanstack/react-query'
import { useWebSocket } from './useWebSocket'
import { LogEntry } from '@/types/log'
import { queryKeys } from '@/lib/react-query/queryKeys'

export const useWebSocketLogs = () => {
  const queryClient = useQueryClient()

  const handleLogMessage = (log: LogEntry) => {
    queryClient.setQueryData<LogEntry[]>(queryKeys.logs.all, (oldData) => {
      if (!oldData) return [log]
      return [log, ...oldData].slice(0, 100)
    })
  }

  return useWebSocket<LogEntry>('/topic/logs', handleLogMessage)
}

