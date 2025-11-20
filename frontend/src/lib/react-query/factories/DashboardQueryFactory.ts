import { IQueryService } from '../interfaces/IQueryService'
import { IDashboardService } from '@/services/api/interfaces/IDashboardService'
import { DashboardStats } from '@/types/dashboard'
import { queryKeys } from '../queryKeys'

export const createDashboardStatsQuery = (
  dashboardService: IDashboardService
): IQueryService<DashboardStats> => ({
  queryKey: queryKeys.dashboard.stats(),
  queryFn: () => dashboardService.getStats(),
})
