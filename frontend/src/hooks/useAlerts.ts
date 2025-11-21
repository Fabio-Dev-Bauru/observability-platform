import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { IUseAlerts } from './interfaces/IUseAlerts'
import { AlertFilters } from '@/types/alert'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'
import { queryKeys } from '@/lib/react-query/queryKeys'
import { useToast } from './useToast'

export function useAlerts(filters?: AlertFilters): IUseAlerts {
  const queryClient = useQueryClient()
  const toast = useToast()
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
      toast.success('Alerta resolvido com sucesso!')
    },
    onError: (error: Error) => {
      toast.error(`Falha ao resolver alerta: ${error.message}`)
    },
  })

  const resolveAlert = async (id: string) => {
    try {
      await resolveMutation.mutateAsync(id)
    } catch (err) {
      // Error já tratado no onError do mutation
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

