'use client'

import { useEffect, useState } from 'react'
import { Alert, AlertSeverity, AlertStatus } from '@/types/alert'
import { alertService } from '@/services/api/alertService'
import { format } from 'date-fns'

export function AlertList() {
  const [alerts, setAlerts] = useState<Alert[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    loadAlerts()
  }, [])

  const loadAlerts = async () => {
    try {
      setLoading(true)
      setError(null)
      const alertEntries = await alertService.getAlerts({ size: 50 })
      setAlerts(alertEntries)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load alerts')
    } finally {
      setLoading(false)
    }
  }

  const handleResolve = async (alertId: string) => {
    try {
      await alertService.resolveAlert(alertId)
      loadAlerts()
    } catch (err) {
      alert('Failed to resolve alert')
    }
  }

  const getSeverityColor = (severity: AlertSeverity): string => {
    switch (severity) {
      case AlertSeverity.CRITICAL:
        return 'text-red-600 bg-red-50 border-red-200'
      case AlertSeverity.HIGH:
        return 'text-orange-600 bg-orange-50 border-orange-200'
      case AlertSeverity.MEDIUM:
        return 'text-yellow-600 bg-yellow-50 border-yellow-200'
      case AlertSeverity.LOW:
        return 'text-blue-600 bg-blue-50 border-blue-200'
      default:
        return 'text-gray-600 bg-gray-50 border-gray-200'
    }
  }

  const getStatusColor = (status: AlertStatus): string => {
    switch (status) {
      case AlertStatus.ACTIVE:
        return 'text-red-600 bg-red-50'
      case AlertStatus.RESOLVED:
        return 'text-green-600 bg-green-50'
      case AlertStatus.ACKNOWLEDGED:
        return 'text-yellow-600 bg-yellow-50'
      default:
        return 'text-gray-600 bg-gray-50'
    }
  }

  if (loading) {
    return (
      <div className="flex justify-center items-center p-8">
        <div className="text-lg">Loading alerts...</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="flex flex-col items-center p-8">
        <div className="text-red-600 mb-4">Error: {error}</div>
        <button
          onClick={loadAlerts}
          className="px-4 py-2 bg-primary-600 text-white rounded hover:bg-primary-700"
        >
          Retry
        </button>
      </div>
    )
  }

  if (alerts.length === 0) {
    return (
      <div className="flex justify-center items-center p-8">
        <div className="text-lg text-gray-500">No alerts found</div>
      </div>
    )
  }

  return (
    <div className="space-y-2">
      {alerts.map((alert) => (
        <div
          key={alert.id}
          className={`border rounded-lg p-4 hover:shadow-md transition-shadow ${getSeverityColor(alert.severity)}`}
        >
          <div className="flex items-start justify-between mb-2">
            <div className="flex-1">
              <div className="flex items-center gap-2 mb-1">
                <span
                  className={`px-2 py-1 rounded text-xs font-semibold ${getSeverityColor(alert.severity)}`}
                >
                  {alert.severity}
                </span>
                <span
                  className={`px-2 py-1 rounded text-xs font-semibold ${getStatusColor(alert.status)}`}
                >
                  {alert.status}
                </span>
                <span className="font-semibold text-sm">{alert.name}</span>
                {alert.service && (
                  <span className="text-xs text-gray-500">@{alert.service}</span>
                )}
              </div>
              {alert.description && (
                <p className="text-sm text-gray-700 mt-1">{alert.description}</p>
              )}
            </div>
            <div className="text-right ml-4">
              {alert.status === AlertStatus.ACTIVE && (
                <button
                  onClick={() => handleResolve(alert.id)}
                  className="px-3 py-1 bg-green-600 text-white text-xs rounded hover:bg-green-700 mb-2"
                >
                  Resolve
                </button>
              )}
              <div className="text-xs text-gray-500">
                {format(new Date(alert.triggeredAt), 'yyyy-MM-dd HH:mm:ss')}
              </div>
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}

