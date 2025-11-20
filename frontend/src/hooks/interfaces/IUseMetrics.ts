import { Metric } from '@/types/metric'
import { IUseQuery } from './IUseQuery'

export interface IUseMetrics extends Omit<IUseQuery<Metric[]>, 'data' | 'refetch'> {
  metrics: Metric[]
  loadMetrics: () => Promise<void>
}

