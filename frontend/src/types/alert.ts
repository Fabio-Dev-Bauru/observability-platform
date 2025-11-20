export enum AlertSeverity {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
  CRITICAL = 'CRITICAL',
}

export enum AlertStatus {
  ACTIVE = 'ACTIVE',
  RESOLVED = 'RESOLVED',
  ACKNOWLEDGED = 'ACKNOWLEDGED',
  SUPPRESSED = 'SUPPRESSED',
}

export interface Alert {
  id: string
  name: string
  description?: string
  severity: AlertSeverity
  status: AlertStatus
  service?: string
  triggeredAt: string
  resolvedAt?: string
  metadata?: Record<string, unknown>
  ruleId?: string
}

export interface AlertRequest {
  name: string
  description?: string
  severity: AlertSeverity
  service?: string
  metadata?: Record<string, unknown>
  ruleId?: string
}

export interface AlertFilters {
  status?: AlertStatus
  severity?: AlertSeverity
  service?: string
  startTime?: string
  endTime?: string
  page?: number
  size?: number
}

