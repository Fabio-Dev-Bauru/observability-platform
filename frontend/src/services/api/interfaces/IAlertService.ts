import { Alert, AlertRequest, AlertFilters } from '@/types/alert'

export interface IAlertService {
  createAlert(alert: AlertRequest): Promise<Alert>
  getAlerts(filters?: AlertFilters): Promise<Alert[]>
  getActiveAlerts(): Promise<Alert[]>
  resolveAlert(id: string): Promise<Alert>
}

