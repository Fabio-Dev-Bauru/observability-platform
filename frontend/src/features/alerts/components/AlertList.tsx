'use client'

import { Alert, AlertSeverity, AlertStatus } from '@/types/alert'
import { useAlerts } from '@/hooks/useAlerts'
import { formatDateTime } from '@/utils/formatters/dateFormatter'
import { mapAlertSeverity, mapAlertStatus } from '@/utils/mappers/badgeVariantMapper'
import { Card, CardContent } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { LoadingState } from '@/components/common/LoadingState'
import { ErrorState } from '@/components/common/ErrorState'
import { EmptyState } from '@/components/common/EmptyState'
import { cn } from '@/lib/utils'

export function AlertList() {
  const { alerts, loading, error, loadAlerts, resolveAlert } = useAlerts({ size: 50 })

  if (loading) {
    return <LoadingState message="Carregando alertas..." />
  }

  if (error) {
    return <ErrorState message={error} onRetry={loadAlerts} />
  }

  if (alerts.length === 0) {
    return <EmptyState message="Nenhum alerta encontrado" />
  }

  return (
    <div className="space-y-2">
      {alerts.map((alert) => (
        <AlertItem key={alert.id} alert={alert} onResolve={resolveAlert} />
      ))}
    </div>
  )
}

interface AlertItemProps {
  alert: Alert
  onResolve: (id: string) => Promise<void>
}

function AlertItem({ alert, onResolve }: AlertItemProps) {
  return (
    <Card 
      className={cn(
        "hover:shadow-md transition-shadow",
        alert.severity === AlertSeverity.CRITICAL && "border-red-200",
        alert.severity === AlertSeverity.HIGH && "border-orange-200"
      )}
    >
      <CardContent className="pt-6">
        <div className="flex items-start justify-between mb-2">
          <div className="flex-1">
            <div className="flex items-center gap-2 mb-1 flex-wrap">
              <Badge variant={mapAlertSeverity(alert.severity)}>
                {alert.severity}
              </Badge>
              <Badge variant={mapAlertStatus(alert.status)}>
                {alert.status}
              </Badge>
              <span className="font-semibold text-sm">{alert.name}</span>
              {alert.service && (
                <span className="text-xs text-muted-foreground">@{alert.service}</span>
              )}
            </div>
            {alert.description && (
              <p className="text-sm mt-1">{alert.description}</p>
            )}
          </div>
          <div className="text-right ml-4">
            {alert.status === AlertStatus.ACTIVE && (
              <Button
                onClick={() => onResolve(alert.id)}
                size="sm"
                variant="default"
                className="mb-2 bg-green-600 hover:bg-green-700"
              >
                Resolver
              </Button>
            )}
            <div className="text-xs text-muted-foreground">
              {formatDateTime(alert.triggeredAt)}
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}

