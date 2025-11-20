import { IAlertService } from '../interfaces/IAlertService'
import { IApiClient } from '../interfaces/IApiClient'
import { Alert, AlertRequest, AlertFilters } from '@/types/alert'
import { QueryParamsBuilder } from '../utils/QueryParamsBuilder'

export class AlertService implements IAlertService {
  constructor(private apiClient: IApiClient) {}

  async createAlert(alert: AlertRequest): Promise<Alert> {
    return this.apiClient.post<Alert>('/alerts', alert)
  }

  async getAlerts(filters?: AlertFilters): Promise<Alert[]> {
    const params = QueryParamsBuilder.fromObject({
      status: filters?.status,
      severity: filters?.severity,
      service: filters?.service,
      startTime: filters?.startTime,
      endTime: filters?.endTime,
      page: filters?.page,
      size: filters?.size,
    })

    const url = params ? `/alerts?${params}` : '/alerts'
    return this.apiClient.get<Alert[]>(url)
  }

  async getActiveAlerts(): Promise<Alert[]> {
    return this.apiClient.get<Alert[]>('/alerts/active')
  }

  async resolveAlert(id: string): Promise<Alert> {
    return this.apiClient.put<Alert>(`/alerts/${id}/resolve`)
  }
}

