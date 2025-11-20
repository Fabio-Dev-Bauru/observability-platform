import { DashboardStats } from '@/types/dashboard'

export interface IDashboardService {
  getStats(): Promise<DashboardStats>
}

