import { DashboardStats } from '@/types/dashboard'

export interface IUseDashboardStats {
  stats: DashboardStats | null
  loading: boolean
  error: string | null
  refetch: () => Promise<void>
}

