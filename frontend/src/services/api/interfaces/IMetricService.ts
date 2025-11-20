import { Metric, MetricRequest, MetricFilters } from '@/types/metric'

export interface IMetricService {
  createMetric(metric: MetricRequest): Promise<Metric>
  getMetrics(filters?: MetricFilters): Promise<Metric[]>
}

