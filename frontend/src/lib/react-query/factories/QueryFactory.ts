import { createLogListQuery, createLogCreateMutation } from './LogQueryFactory'
import { createMetricListQuery, createMetricCreateMutation } from './MetricQueryFactory'
import { createAlertListQuery, createActiveAlertsQuery, createAlertCreateMutation, createAlertResolveMutation } from './AlertQueryFactory'
import { createDashboardStatsQuery } from './DashboardQueryFactory'
import { ILogService } from '@/services/api/interfaces/ILogService'
import { IMetricService } from '@/services/api/interfaces/IMetricService'
import { IAlertService } from '@/services/api/interfaces/IAlertService'
import { IDashboardService } from '@/services/api/interfaces/IDashboardService'
import { logService, metricService, alertService, dashboardService } from '@/services/api/factory/ServiceFactory'

export const queryFactory = {
  log: {
    createListQuery: (filters?: Parameters<typeof createLogListQuery>[1]) =>
      createLogListQuery(logService, filters),
    createCreateMutation: () => createLogCreateMutation(logService),
  },
  metric: {
    createListQuery: (filters?: Parameters<typeof createMetricListQuery>[1]) =>
      createMetricListQuery(metricService, filters),
    createCreateMutation: () => createMetricCreateMutation(metricService),
  },
  alert: {
    createListQuery: (filters?: Parameters<typeof createAlertListQuery>[1]) =>
      createAlertListQuery(alertService, filters),
    createActiveQuery: () => createActiveAlertsQuery(alertService),
    createCreateMutation: () => createAlertCreateMutation(alertService),
    createResolveMutation: () => createAlertResolveMutation(alertService),
  },
  dashboard: {
    createStatsQuery: () => createDashboardStatsQuery(dashboardService),
  },
}
