import { useQuery } from '@tanstack/react-query'
import { IUseLogs } from './interfaces/IUseLogs'
import { LogFilters } from '@/types/log'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'
import { useToast } from './useToast'

export function useLogs(filters?: LogFilters): IUseLogs {
  const toast = useToast()
  const query = queryFactory.log.createListQuery(filters)

  const { data: logs = [], isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: 30000,
  })

  const loadLogs = async () => {
    try {
      await refetch()
      toast.success('Logs atualizados com sucesso!')
    } catch (err) {
      toast.error('Falha ao atualizar logs')
    }
  }

  return {
    logs,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Falha ao carregar logs') : null,
    loadLogs,
  }
}

