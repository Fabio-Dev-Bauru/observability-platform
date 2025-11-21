import { useQuery } from '@tanstack/react-query'
import { IUseMetrics } from './interfaces/IUseMetrics'
import { MetricFilters } from '@/types/metric'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'
import { useToast } from './useToast'

export function useMetrics(filters?: MetricFilters): IUseMetrics {
  const toast = useToast()
  const query = queryFactory.metric.createListQuery(filters)

  const { data: metrics = [], isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: 30000,
    onError: () => {
      toast.error('Falha ao carregar métricas')
    },
  })

  const loadMetrics = async () => {
    try {
      await refetch()
      toast.success('Métricas atualizadas com sucesso!')
    } catch (err) {
      toast.error('Falha ao atualizar métricas')
    }
  }

  return {
    metrics,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Falha ao carregar métricas') : null,
    loadMetrics,
  }
}

