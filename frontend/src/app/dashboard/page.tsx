'use client'

import { useDashboardStats } from '@/hooks/useDashboardStats'
import { useMetricsTimeSeries } from '@/hooks/useMetricsTimeSeries'
import { useWebSocketLogs } from '@/hooks/useWebSocketLogs'
import { useWebSocketMetrics } from '@/hooks/useWebSocketMetrics'
import { useWebSocketAlerts } from '@/hooks/useWebSocketAlerts'
import { StatCard } from '@/components/ui/StatCard'
import { LogsByLevelChart } from '@/components/charts/LogsByLevelChart'
import { MetricsTimeSeriesChart } from '@/components/charts/MetricsTimeSeriesChart'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { LoadingState } from '@/components/common/LoadingState'
import { ErrorState } from '@/components/common/ErrorState'
import { EmptyState } from '@/components/common/EmptyState'
import { PageLayout } from '@/components/layout/PageLayout'

export default function DashboardPage() {
  const { stats, loading, error, refetch } = useDashboardStats()
  const { metrics, selectedMetric, setSelectedMetric } = useMetricsTimeSeries()
  
  useWebSocketLogs()
  useWebSocketMetrics()
  useWebSocketAlerts()

  if (loading) {
    return (
      <PageLayout>
        <LoadingState message="Carregando dashboard..." />
      </PageLayout>
    )
  }

  if (error) {
    return (
      <PageLayout>
        <ErrorState message={error} onRetry={refetch} />
      </PageLayout>
    )
  }

  return (
    <PageLayout title="Dashboard" description="Visão geral do sistema de observabilidade">
      <DashboardStats stats={stats} />
      <DashboardCharts stats={stats} metrics={metrics} selectedMetric={selectedMetric} setSelectedMetric={setSelectedMetric} />
    </PageLayout>
  )
}

import { DashboardStats as DashboardStatsType } from '@/types/dashboard'
import { Metric } from '@/types/metric'

interface DashboardStatsProps {
  stats: DashboardStatsType | null
}

function DashboardStats({ stats }: DashboardStatsProps) {
  if (!stats) return null

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
      <StatCard
        title="Total Logs (24h)"
        value={stats.totalLogs?.toLocaleString() || '0'}
        subtitle={`${stats.totalLogsLastHour || 0} in last hour`}
        color="primary"
      />
      <StatCard
        title="Error Rate"
        value={`${stats.errorRate?.toFixed(2) || '0'}%`}
        subtitle={`${stats.errorLogs || 0} errors in last hour`}
        color="red"
      />
      <StatCard
        title="Active Alerts"
        value={stats.activeAlerts || 0}
        color="yellow"
      />
      <StatCard
        title="Metrics"
        value={stats.totalMetrics || 0}
        subtitle="Last hour"
        color="blue"
      />
    </div>
  )
}

interface DashboardChartsProps {
  stats: DashboardStatsType | null
  metrics: Metric[]
  selectedMetric: string
  setSelectedMetric: (name: string) => void
}

function DashboardCharts({ stats, metrics, selectedMetric, setSelectedMetric }: DashboardChartsProps) {
  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
      <Card>
        <CardHeader>
          <CardTitle>Logs por Nível (Última Hora)</CardTitle>
        </CardHeader>
        <CardContent>
          {stats?.logsByLevel ? (
            <LogsByLevelChart data={stats.logsByLevel} />
          ) : (
            <EmptyState message="Nenhum dado disponível" />
          )}
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Série Temporal de Métricas</CardTitle>
        </CardHeader>
        <CardContent>
          {metrics.length > 0 ? (
            <div>
              <select
                value={selectedMetric}
                onChange={(e) => setSelectedMetric(e.target.value)}
                className="mb-4 px-3 py-2 border rounded-lg w-full bg-background"
              >
                {[...new Set(metrics.map(m => m.name))].map(name => (
                  <option key={name} value={name}>{name}</option>
                ))}
              </select>
              <MetricsTimeSeriesChart metrics={metrics} metricName={selectedMetric} />
            </div>
          ) : (
            <EmptyState message="Nenhuma métrica disponível" />
          )}
        </CardContent>
      </Card>
    </div>
  )
}
