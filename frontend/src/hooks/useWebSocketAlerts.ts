import { useQueryClient } from '@tanstack/react-query'
import { useWebSocket } from './useWebSocket'
import { Alert } from '@/types/alert'
import { queryKeys } from '@/lib/react-query/queryKeys'

export const useWebSocketAlerts = () => {
  const queryClient = useQueryClient()

  const handleAlertMessage = (alert: Alert) => {
    queryClient.setQueryData<Alert[]>(queryKeys.alerts.all, (oldData) => {
      if (!oldData) return [alert]
      return [alert, ...oldData].slice(0, 100)
    })
    
    queryClient.invalidateQueries({ queryKey: queryKeys.dashboard.stats() })
  }

  return useWebSocket<Alert>('/topic/alerts', handleAlertMessage)
}

