export interface DashboardStats {
  totalLogs: number
  totalLogsLastHour: number
  errorLogs: number
  errorRate: number
  activeAlerts: number
  totalMetrics: number
  logsByLevel: Record<string, number>
}

