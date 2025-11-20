import { IQueryService, IMutationService } from '../interfaces/IQueryService'
import { IAlertService } from '@/services/api/interfaces/IAlertService'
import { Alert, AlertRequest, AlertFilters } from '@/types/alert'
import { queryKeys } from '../queryKeys'
import { queryClient } from '../queryClient'

export const createAlertListQuery = (
  alertService: IAlertService,
  filters?: AlertFilters
): IQueryService<Alert[], AlertFilters> => ({
  queryKey: queryKeys.alerts.list(filters),
  queryFn: () => alertService.getAlerts(filters),
})

export const createActiveAlertsQuery = (
  alertService: IAlertService
): IQueryService<Alert[]> => ({
  queryKey: queryKeys.alerts.active(),
  queryFn: () => alertService.getActiveAlerts(),
})

export const createAlertCreateMutation = (
  alertService: IAlertService
): IMutationService<Alert, AlertRequest> => ({
  mutationFn: (variables: AlertRequest) => alertService.createAlert(variables),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: queryKeys.alerts.all })
  },
})

export const createAlertResolveMutation = (
  alertService: IAlertService
): IMutationService<Alert, string> => ({
  mutationFn: (id: string) => alertService.resolveAlert(id),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: queryKeys.alerts.all })
  },
})
