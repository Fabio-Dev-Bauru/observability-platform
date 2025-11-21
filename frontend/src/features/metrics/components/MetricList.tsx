'use client'

import { Metric } from '@/types/metric'
import { useMetrics } from '@/hooks/useMetrics'
import { useWebSocketMetrics } from '@/hooks/useWebSocketMetrics'
import { formatDateTime } from '@/utils/formatters/dateFormatter'
import { Card, CardContent } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { LoadingState } from '@/components/common/LoadingState'
import { ErrorState } from '@/components/common/ErrorState'
import { EmptyState } from '@/components/common/EmptyState'

export function MetricList() {
  const { metrics, loading, error, loadMetrics } = useMetrics({ size: 50 })
  useWebSocketMetrics()

  if (loading) {
    return <LoadingState message="Carregando métricas..." />
  }

  if (error) {
    return <ErrorState message={error} onRetry={loadMetrics} />
  }

  if (metrics.length === 0) {
    return <EmptyState message="Nenhuma métrica encontrada" />
  }

  return (
    <div className="space-y-2">
      {metrics.map((metric) => (
        <MetricItem key={metric.id} metric={metric} />
      ))}
    </div>
  )
}

interface MetricItemProps {
  metric: Metric
}

function MetricItem({ metric }: MetricItemProps) {
  return (
    <Card className="hover:shadow-md transition-shadow">
      <CardContent className="pt-6">
        <div className="flex items-start justify-between mb-2">
          <div className="flex items-center gap-2 flex-wrap">
            <Badge variant="secondary">{metric.type}</Badge>
            <span className="font-semibold text-sm">{metric.name}</span>
            {metric.service && (
              <span className="text-xs text-muted-foreground">@{metric.service}</span>
            )}
          </div>
          <div className="text-right">
            <div className="text-lg font-bold text-primary-600">{metric.value}</div>
            <span className="text-xs text-muted-foreground">
              {formatDateTime(metric.timestamp)}
            </span>
          </div>
        </div>
        {metric.labels && Object.keys(metric.labels).length > 0 && (
          <div className="mt-2 flex flex-wrap gap-1">
            {Object.entries(metric.labels).map(([key, value]) => (
              <Badge key={key} variant="outline" className="text-xs">
                {key}: {value}
              </Badge>
            ))}
          </div>
        )}
      </CardContent>
    </Card>
  )
}

