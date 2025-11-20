import { LogLevel } from '@/types/log'
import { AlertSeverity, AlertStatus } from '@/types/alert'
import { VariantProps } from 'class-variance-authority'
import { badgeVariants } from '@/components/ui/badge'

type BadgeVariant = VariantProps<typeof badgeVariants>['variant']

export const mapLogLevel = (level: LogLevel): BadgeVariant => {
  switch (level) {
    case LogLevel.ERROR:
    case LogLevel.FATAL:
      return 'destructive'
    case LogLevel.WARN:
      return 'warning'
    default:
      return 'default'
  }
}

export const mapAlertSeverity = (severity: AlertSeverity): BadgeVariant => {
  switch (severity) {
    case AlertSeverity.CRITICAL:
      return 'destructive'
    case AlertSeverity.HIGH:
      return 'warning'
    case AlertSeverity.MEDIUM:
      return 'info'
    case AlertSeverity.LOW:
      return 'secondary'
    default:
      return 'default'
  }
}

export const mapAlertStatus = (status: AlertStatus): BadgeVariant => {
  switch (status) {
    case AlertStatus.ACTIVE:
      return 'destructive'
    case AlertStatus.RESOLVED:
      return 'success'
    default:
      return 'secondary'
  }
}

