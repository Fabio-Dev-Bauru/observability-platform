'use client'

import { useDashboardStats } from '@/hooks/useDashboardStats'
import { useMetricsTimeSeries } from '@/hooks/useMetricsTimeSeries'
import { StatCard } from '@/components/ui/StatCard'
import { LogsByLevelChart } from '@/components/charts/LogsByLevelChart'
import { MetricsTimeSeriesChart } from '@/components/charts/MetricsTimeSeriesChart'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { LoadingState } from '@/components/common/LoadingState'
import { ErrorState } from '@/components/common/ErrorState'
import { EmptyState } from '@/components/common/EmptyState'

export default function DashboardPage() {
  const { stats, loading, error, refetch } = useDashboardStats()
  const { metrics, selectedMetric, setSelectedMetric } = useMetricsTimeSeries()

  if (loading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <LoadingState message="Carregando dashboard..." />
      </div>
    )
  }

  if (error) {
    return (
      <div className="container mx-auto px-4 py-8">
        <ErrorState message={error} onRetry={refetch} />
      </div>
    )
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>
      
      <DashboardStats stats={stats} />
      <DashboardCharts stats={stats} metrics={metrics} selectedMetric={selectedMetric} setSelectedMetric={setSelectedMetric} />
    </div>
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
