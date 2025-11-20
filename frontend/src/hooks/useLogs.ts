import { useQuery } from '@tanstack/react-query'
import { IUseLogs } from './interfaces/IUseLogs'
import { LogFilters } from '@/types/log'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'

export function useLogs(filters?: LogFilters): IUseLogs {
  const query = queryFactory.log.createListQuery(filters)

  const { data: logs = [], isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: 30000,
  })

  return {
    logs,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Failed to load logs') : null,
    loadLogs: async () => {
      await refetch()
    },
  }
}

