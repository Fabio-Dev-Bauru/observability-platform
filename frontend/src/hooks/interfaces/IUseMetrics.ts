import { Metric } from '@/types/metric'

export interface IUseMetrics {
  metrics: Metric[]
  loading: boolean
  error: string | null
  loadMetrics: () => Promise<void>
}

