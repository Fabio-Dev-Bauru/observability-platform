'use client'

import { useEffect, useState } from 'react'
import { Metric, MetricType } from '@/types/metric'
import { metricService } from '@/services/api/metricService'
import { format } from 'date-fns'

export function MetricList() {
  const [metrics, setMetrics] = useState<Metric[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    loadMetrics()
  }, [])

  const loadMetrics = async () => {
    try {
      setLoading(true)
      setError(null)
      const metricEntries = await metricService.getMetrics({ size: 50 })
      setMetrics(metricEntries)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load metrics')
    } finally {
      setLoading(false)
    }
  }

  const getTypeColor = (type: MetricType): string => {
    switch (type) {
      case MetricType.COUNTER:
        return 'text-blue-600 bg-blue-50'
      case MetricType.GAUGE:
        return 'text-green-600 bg-green-50'
      case MetricType.HISTOGRAM:
        return 'text-purple-600 bg-purple-50'
      case MetricType.SUMMARY:
        return 'text-orange-600 bg-orange-50'
      default:
        return 'text-gray-600 bg-gray-50'
    }
  }

  if (loading) {
    return (
      <div className="flex justify-center items-center p-8">
        <div className="text-lg">Loading metrics...</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="flex flex-col items-center p-8">
        <div className="text-red-600 mb-4">Error: {error}</div>
        <button
          onClick={loadMetrics}
          className="px-4 py-2 bg-primary-600 text-white rounded hover:bg-primary-700"
        >
          Retry
        </button>
      </div>
    )
  }

  if (metrics.length === 0) {
    return (
      <div className="flex justify-center items-center p-8">
        <div className="text-lg text-gray-500">No metrics found</div>
      </div>
    )
  }

  return (
    <div className="space-y-2">
      {metrics.map((metric) => (
        <div
          key={metric.id}
          className="border rounded-lg p-4 hover:shadow-md transition-shadow"
        >
          <div className="flex items-start justify-between mb-2">
            <div className="flex items-center gap-2">
              <span
                className={`px-2 py-1 rounded text-xs font-semibold ${getTypeColor(metric.type)}`}
              >
                {metric.type}
              </span>
              <span className="font-semibold text-sm">{metric.name}</span>
              {metric.service && (
                <span className="text-xs text-gray-500">@{metric.service}</span>
              )}
            </div>
            <div className="text-right">
              <div className="text-lg font-bold text-primary-600">{metric.value}</div>
              <span className="text-xs text-gray-500">
                {format(new Date(metric.timestamp), 'yyyy-MM-dd HH:mm:ss')}
              </span>
            </div>
          </div>
          {metric.labels && Object.keys(metric.labels).length > 0 && (
            <div className="mt-2 flex flex-wrap gap-1">
              {Object.entries(metric.labels).map(([key, value]) => (
                <span
                  key={key}
                  className="text-xs bg-gray-100 px-2 py-1 rounded"
                >
                  {key}: {value}
                </span>
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  )
}

