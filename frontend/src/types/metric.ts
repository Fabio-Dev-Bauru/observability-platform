export enum MetricType {
  COUNTER = 'COUNTER',
  GAUGE = 'GAUGE',
  HISTOGRAM = 'HISTOGRAM',
  SUMMARY = 'SUMMARY',
}

export interface Metric {
  id: string
  name: string
  value: number
  service?: string
  host?: string
  timestamp: string
  labels?: Record<string, string>
  type: MetricType
}

export interface MetricRequest {
  name: string
  value: number
  service?: string
  host?: string
  timestamp?: string
  labels?: Record<string, string>
  type: MetricType
}

export interface MetricFilters {
  name?: string
  service?: string
  host?: string
  startTime?: string
  endTime?: string
  page?: number
  size?: number
}

