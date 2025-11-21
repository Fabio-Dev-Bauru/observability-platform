import { useQueryClient } from '@tanstack/react-query'
import { useWebSocket } from './useWebSocket'
import { Metric } from '@/types/metric'
import { queryKeys } from '@/lib/react-query/queryKeys'

export const useWebSocketMetrics = () => {
  const queryClient = useQueryClient()

  const handleMetricMessage = (metric: Metric) => {
    queryClient.setQueryData<Metric[]>(queryKeys.metrics.all, (oldData) => {
      if (!oldData) return [metric]
      return [metric, ...oldData].slice(0, 100)
    })
  }

  return useWebSocket<Metric>('/topic/metrics', handleMetricMessage)
}

