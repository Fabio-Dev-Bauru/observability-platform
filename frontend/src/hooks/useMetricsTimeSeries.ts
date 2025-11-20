import { useState, useEffect } from 'react'
import { useQuery } from '@tanstack/react-query'
import { Metric } from '@/types/metric'
import { queryFactory } from '@/lib/react-query/factories/QueryFactory'

interface UseMetricsTimeSeriesResult {
  metrics: Metric[]
  selectedMetric: string
  setSelectedMetric: (name: string) => void
  loading: boolean
  error: string | null
}

export function useMetricsTimeSeries(refreshInterval: number = 30000): UseMetricsTimeSeriesResult {
  const [selectedMetric, setSelectedMetric] = useState<string>('')
  const query = queryFactory.metric.createListQuery({ size: 100 })

  const { data: metrics = [], isLoading: loading, error } = useQuery({
    queryKey: query.queryKey,
    queryFn: query.queryFn,
    refetchInterval: refreshInterval,
  })

  useEffect(() => {
    if (metrics.length > 0 && !selectedMetric) {
      const uniqueNames = [...new Set(metrics.map(m => m.name))]
      setSelectedMetric(uniqueNames[0])
    }
  }, [metrics, selectedMetric])

  return {
    metrics,
    selectedMetric,
    setSelectedMetric,
    loading,
    error: error ? (error instanceof Error ? error.message : 'Failed to load metrics') : null,
  }
}

