import { useQuery } from '@tanstack/react-query'
import { IUseMetrics } from './interfaces/IUseMetrics'
import { MetricFilters } from '@/types/metric'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'

export function useMetrics(filters?: MetricFilters): IUseMetrics {
  const query = queryFactory.metric.createListQuery(filters)

  const { data: metrics = [], isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: 30000,
  })

  return {
    metrics,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Failed to load metrics') : null,
    loadMetrics: async () => {
      await refetch()
    },
  }
}

