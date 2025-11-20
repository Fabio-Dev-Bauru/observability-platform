import { AxiosApiClient } from '../clients/AxiosApiClient'
import { IApiClient } from '../interfaces/IApiClient'
import { ILogService } from '../interfaces/ILogService'
import { IMetricService } from '../interfaces/IMetricService'
import { IAlertService } from '../interfaces/IAlertService'
import { IDashboardService } from '../interfaces/IDashboardService'
import { LogService } from '../implementations/LogService'
import { MetricService } from '../implementations/MetricService'
import { AlertService } from '../implementations/AlertService'
import { DashboardService } from '../implementations/DashboardService'

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'
const BASE_PATH = `${API_URL}/api/v1`

class ServiceFactory {
  private apiClient: IApiClient

  constructor() {
    this.apiClient = new AxiosApiClient(BASE_PATH)
  }

  createLogService(): ILogService {
    return new LogService(this.apiClient)
  }

  createMetricService(): IMetricService {
    return new MetricService(this.apiClient)
  }

  createAlertService(): IAlertService {
    return new AlertService(this.apiClient)
  }

  createDashboardService(): IDashboardService {
    return new DashboardService(this.apiClient)
  }
}

export const serviceFactory = new ServiceFactory()

export const logService = serviceFactory.createLogService()
export const metricService = serviceFactory.createMetricService()
export const alertService = serviceFactory.createAlertService()
export const dashboardService = serviceFactory.createDashboardService()

