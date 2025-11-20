import { IMetricService } from '../interfaces/IMetricService'
import { IApiClient } from '../interfaces/IApiClient'
import { Metric, MetricRequest, MetricFilters } from '@/types/metric'
import { buildQueryParams } from '../utils/QueryParamsBuilder'

export class MetricService implements IMetricService {
  constructor(private apiClient: IApiClient) {}

  async createMetric(metric: MetricRequest): Promise<Metric> {
    return this.apiClient.post<Metric>('/metrics', metric)
  }

  async getMetrics(filters?: MetricFilters): Promise<Metric[]> {
    const params = buildQueryParams({
      name: filters?.name,
      service: filters?.service,
      host: filters?.host,
      startTime: filters?.startTime,
      endTime: filters?.endTime,
      page: filters?.page,
      size: filters?.size,
    })

    const url = params ? `/metrics?${params}` : '/metrics'
    return this.apiClient.get<Metric[]>(url)
  }
}

