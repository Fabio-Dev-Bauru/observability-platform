import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { IUseAlerts } from './interfaces/IUseAlerts'
import { AlertFilters } from '@/types/alert'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'
import { queryKeys } from '@/lib/react-query/queryKeys'

export function useAlerts(filters?: AlertFilters): IUseAlerts {
  const queryClient = useQueryClient()
  const query = queryFactory.alert.createListQuery(filters)

  const { data: alerts = [], isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: 30000,
  })

  const resolveMutation = useMutation({
    mutationFn: (id: string) => queryFactory.alert.createResolveMutation().mutationFn(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: queryKeys.alerts.all })
    },
  })

  const resolveAlert = async (id: string) => {
    try {
      await resolveMutation.mutateAsync(id)
    } catch (err) {
      throw new Error(err instanceof Error ? err.message : 'Falha ao resolver alerta')
    }
  }

  return {
    alerts,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Falha ao carregar alertas') : null,
    loadAlerts: async () => {
      await refetch()
    },
    resolveAlert,
  }
}

