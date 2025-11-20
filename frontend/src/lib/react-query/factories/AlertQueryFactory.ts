import { IQueryService, IMutationService } from '../interfaces/IQueryService'
import { IAlertService } from '@/services/api/interfaces/IAlertService'
import { Alert, AlertRequest, AlertFilters } from '@/types/alert'
import { queryKeys } from '../queryKeys'
import { queryClient } from '../queryClient'

export class AlertQueryFactory {
  constructor(private alertService: IAlertService) {}

  createListQuery(filters?: AlertFilters): IQueryService<Alert[], AlertFilters> {
    return {
      queryKey: queryKeys.alerts.list(filters),
      queryFn: () => this.alertService.getAlerts(filters),
    }
  }

  createActiveQuery(): IQueryService<Alert[]> {
    return {
      queryKey: queryKeys.alerts.active(),
      queryFn: () => this.alertService.getActiveAlerts(),
    }
  }

  createCreateMutation(): IMutationService<Alert, AlertRequest> {
    return {
      mutationFn: (variables: AlertRequest) => this.alertService.createAlert(variables),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.alerts.all })
      },
    }
  }

  createResolveMutation(): IMutationService<Alert, string> {
    return {
      mutationFn: (id: string) => this.alertService.resolveAlert(id),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.alerts.all })
      },
    }
  }
}

