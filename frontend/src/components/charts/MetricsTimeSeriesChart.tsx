'use client'

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import { Metric } from '@/types/metric'
import { format } from 'date-fns'

interface MetricsTimeSeriesChartProps {
  metrics: Metric[]
  metricName?: string
}

export function MetricsTimeSeriesChart({ metrics, metricName }: MetricsTimeSeriesChartProps) {
  if (!metricName) {
    return (
      <div className="flex items-center justify-center h-64 text-gray-500">
        Select a metric to view chart
      </div>
    )
  }

  const filteredMetrics = metrics
    .filter(m => m.name === metricName)
    .slice(-20)
    .map(m => ({
      time: format(new Date(m.timestamp), 'HH:mm:ss'),
      value: m.value,
    }))

  if (filteredMetrics.length === 0) {
    return (
      <div className="flex items-center justify-center h-64 text-gray-500">
        No data available for {metricName}
      </div>
    )
  }

  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart data={filteredMetrics}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="time" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="value" stroke="#3B82F6" strokeWidth={2} />
      </LineChart>
    </ResponsiveContainer>
  )
}

