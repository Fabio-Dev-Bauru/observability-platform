'use client'

import { useQuery } from '@tanstack/react-query'
import { IUseDashboardStats } from './interfaces/IUseDashboardStats'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'

export function useDashboardStats(refreshInterval: number = 30000): IUseDashboardStats {
  const query = queryFactory.dashboard.createStatsQuery()

  const { data: stats = null, isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: refreshInterval,
  })

  return {
    stats,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Failed to load dashboard stats') : null,
    refetch: async () => {
      await refetch()
    },
  }
}

