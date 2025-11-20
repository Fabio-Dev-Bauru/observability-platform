'use client'

import { useDashboardStats } from '@/hooks/useDashboardStats'
import { StatCard } from '@/components/ui/StatCard'
import { LogsByLevelChart } from '@/components/charts/LogsByLevelChart'
import { useEffect, useState } from 'react'
import { metricService } from '@/services/api/metricService'
import { Metric } from '@/types/metric'
import { MetricsTimeSeriesChart } from '@/components/charts/MetricsTimeSeriesChart'

export default function DashboardPage() {
  const { stats, loading, error } = useDashboardStats()
  const [recentMetrics, setRecentMetrics] = useState<Metric[]>([])
  const [selectedMetric, setSelectedMetric] = useState<string>('')

  useEffect(() => {
    const loadMetrics = async () => {
      try {
        const metrics = await metricService.getMetrics({ size: 100 })
        setRecentMetrics(metrics)
        if (metrics.length > 0 && !selectedMetric) {
          const uniqueNames = [...new Set(metrics.map(m => m.name))]
          setSelectedMetric(uniqueNames[0])
        }
      } catch (err) {
        console.error('Failed to load metrics:', err)
      }
    }
    loadMetrics()
    const interval = setInterval(loadMetrics, 30000)
    return () => clearInterval(interval)
  }, [selectedMetric])

  if (loading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="flex justify-center items-center h-64">
          <div className="text-lg">Loading dashboard...</div>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="bg-red-50 border border-red-200 rounded-lg p-4">
          <p className="text-red-600">Error: {error}</p>
        </div>
      </div>
    )
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <StatCard
          title="Total Logs (24h)"
          value={stats?.totalLogs?.toLocaleString() || '0'}
          subtitle={`${stats?.totalLogsLastHour || 0} in last hour`}
          color="primary"
        />
        <StatCard
          title="Error Rate"
          value={`${stats?.errorRate?.toFixed(2) || '0'}%`}
          subtitle={`${stats?.errorLogs || 0} errors in last hour`}
          color="red"
        />
        <StatCard
          title="Active Alerts"
          value={stats?.activeAlerts || 0}
          color="yellow"
        />
        <StatCard
          title="Metrics"
          value={stats?.totalMetrics || 0}
          subtitle="Last hour"
          color="blue"
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-lg font-semibold mb-4">Logs by Level (Last Hour)</h2>
          {stats?.logsByLevel ? (
            <LogsByLevelChart data={stats.logsByLevel} />
          ) : (
            <div className="flex items-center justify-center h-64 text-gray-500">
              No data available
            </div>
          )}
        </div>

        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-lg font-semibold mb-4">Metrics Time Series</h2>
          {recentMetrics.length > 0 ? (
            <div>
              <select
                value={selectedMetric}
                onChange={(e) => setSelectedMetric(e.target.value)}
                className="mb-4 px-3 py-2 border rounded-lg"
              >
                {[...new Set(recentMetrics.map(m => m.name))].map(name => (
                  <option key={name} value={name}>{name}</option>
                ))}
              </select>
              <MetricsTimeSeriesChart metrics={recentMetrics} metricName={selectedMetric} />
            </div>
          ) : (
            <div className="flex items-center justify-center h-64 text-gray-500">
              No metrics available
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
