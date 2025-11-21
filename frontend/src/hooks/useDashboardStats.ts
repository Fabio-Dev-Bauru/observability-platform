'use client'

import { useQuery } from '@tanstack/react-query'
import { IUseDashboardStats } from './interfaces/IUseDashboardStats'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'
import { useToast } from './useToast'

export function useDashboardStats(refreshInterval: number = 30000): IUseDashboardStats {
  const toast = useToast()
  const query = queryFactory.dashboard.createStatsQuery()

  const { data: stats = null, isLoading: loading, error, refetch } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: refreshInterval,
  })

  const refetchStats = async () => {
    try {
      await refetch()
      toast.success('Dashboard atualizado com sucesso!')
    } catch (err) {
      toast.error('Falha ao atualizar dashboard')
    }
  }

  return {
    stats,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Falha ao carregar estatísticas do dashboard') : null,
    refetch: refetchStats,
  }
}

