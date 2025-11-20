import { IQueryService } from '../interfaces/IQueryService'
import { IDashboardService } from '@/services/api/interfaces/IDashboardService'
import { DashboardStats } from '@/types/dashboard'
import { queryKeys } from '../queryKeys'

export class DashboardQueryFactory {
  constructor(private dashboardService: IDashboardService) {}

  createStatsQuery(): IQueryService<DashboardStats> {
    return {
      queryKey: queryKeys.dashboard.stats(),
      queryFn: () => this.dashboardService.getStats(),
    }
  }
}

