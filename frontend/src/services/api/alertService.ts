import axios from 'axios'
import { Alert, AlertRequest, AlertFilters } from '@/types/alert'

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'

const apiClient = axios.create({
  baseURL: `${API_URL}/api/v1`,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const alertService = {
  async createAlert(alert: AlertRequest): Promise<Alert> {
    const response = await apiClient.post<Alert>('/alerts', alert)
    return response.data
  },

  async getAlerts(filters?: AlertFilters): Promise<Alert[]> {
    const params = new URLSearchParams()
    
    if (filters?.status) params.append('status', filters.status)
    if (filters?.severity) params.append('severity', filters.severity)
    if (filters?.service) params.append('service', filters.service)
    if (filters?.startTime) params.append('startTime', filters.startTime)
    if (filters?.endTime) params.append('endTime', filters.endTime)
    if (filters?.page !== undefined) params.append('page', filters.page.toString())
    if (filters?.size !== undefined) params.append('size', filters.size.toString())

    const response = await apiClient.get<Alert[]>(`/alerts?${params.toString()}`)
    return response.data
  },

  async getActiveAlerts(): Promise<Alert[]> {
    const response = await apiClient.get<Alert[]>('/alerts/active')
    return response.data
  },

  async resolveAlert(id: string): Promise<Alert> {
    const response = await apiClient.put<Alert>(`/alerts/${id}/resolve`)
    return response.data
  },
}

