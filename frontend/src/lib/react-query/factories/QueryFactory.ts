import { LogQueryFactory } from './LogQueryFactory'
import { MetricQueryFactory } from './MetricQueryFactory'
import { AlertQueryFactory } from './AlertQueryFactory'
import { DashboardQueryFactory } from './DashboardQueryFactory'
import { ILogService } from '@/services/api/interfaces/ILogService'
import { IMetricService } from '@/services/api/interfaces/IMetricService'
import { IAlertService } from '@/services/api/interfaces/IAlertService'
import { IDashboardService } from '@/services/api/interfaces/IDashboardService'
import { logService, metricService, alertService, dashboardService } from '@/services/api/factory/ServiceFactory'

class QueryFactory {
  private logQueryFactory: LogQueryFactory
  private metricQueryFactory: MetricQueryFactory
  private alertQueryFactory: AlertQueryFactory
  private dashboardQueryFactory: DashboardQueryFactory

  constructor(
    logService: ILogService = logService,
    metricService: IMetricService = metricService,
    alertService: IAlertService = alertService,
    dashboardService: IDashboardService = dashboardService
  ) {
    this.logQueryFactory = new LogQueryFactory(logService)
    this.metricQueryFactory = new MetricQueryFactory(metricService)
    this.alertQueryFactory = new AlertQueryFactory(alertService)
    this.dashboardQueryFactory = new DashboardQueryFactory(dashboardService)
  }

  getLogQueryFactory(): LogQueryFactory {
    return this.logQueryFactory
  }

  getMetricQueryFactory(): MetricQueryFactory {
    return this.metricQueryFactory
  }

  getAlertQueryFactory(): AlertQueryFactory {
    return this.alertQueryFactory
  }

  getDashboardQueryFactory(): DashboardQueryFactory {
    return this.dashboardQueryFactory
  }
}

export const queryFactory = new QueryFactory()

