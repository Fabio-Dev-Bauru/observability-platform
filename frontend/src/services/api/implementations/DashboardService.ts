import { IDashboardService } from '../interfaces/IDashboardService'
import { IApiClient } from '../interfaces/IApiClient'
import { DashboardStats } from '@/types/dashboard'

export class DashboardService implements IDashboardService {
  constructor(private apiClient: IApiClient) {}

  async getStats(): Promise<DashboardStats> {
    return this.apiClient.get<DashboardStats>('/dashboard/stats')
  }
}

