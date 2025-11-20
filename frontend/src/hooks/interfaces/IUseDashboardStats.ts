import { DashboardStats } from '@/types/dashboard'
import { IUseQuery } from './IUseQuery'

export interface IUseDashboardStats extends Omit<IUseQuery<DashboardStats | null>, 'data'> {
  stats: DashboardStats | null
}

