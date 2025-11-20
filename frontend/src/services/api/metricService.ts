import axios from 'axios'
import { Metric, MetricRequest, MetricFilters } from '@/types/metric'

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'

const apiClient = axios.create({
  baseURL: `${API_URL}/api/v1`,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const metricService = {
  async createMetric(metric: MetricRequest): Promise<Metric> {
    const response = await apiClient.post<Metric>('/metrics', metric)
    return response.data
  },

  async getMetrics(filters?: MetricFilters): Promise<Metric[]> {
    const params = new URLSearchParams()
    
    if (filters?.name) params.append('name', filters.name)
    if (filters?.service) params.append('service', filters.service)
    if (filters?.host) params.append('host', filters.host)
    if (filters?.startTime) params.append('startTime', filters.startTime)
    if (filters?.endTime) params.append('endTime', filters.endTime)
    if (filters?.page !== undefined) params.append('page', filters.page.toString())
    if (filters?.size !== undefined) params.append('size', filters.size.toString())

    const response = await apiClient.get<Metric[]>(`/metrics?${params.toString()}`)
    return response.data
  },
}

