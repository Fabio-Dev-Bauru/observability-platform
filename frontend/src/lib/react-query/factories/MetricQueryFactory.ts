import { IQueryService, IMutationService } from '../interfaces/IQueryService'
import { IMetricService } from '@/services/api/interfaces/IMetricService'
import { Metric, MetricRequest, MetricFilters } from '@/types/metric'
import { queryKeys } from '../queryKeys'
import { queryClient } from '../queryClient'

export class MetricQueryFactory {
  constructor(private metricService: IMetricService) {}

  createListQuery(filters?: MetricFilters): IQueryService<Metric[], MetricFilters> {
    return {
      queryKey: queryKeys.metrics.list(filters),
      queryFn: () => this.metricService.getMetrics(filters),
    }
  }

  createCreateMutation(): IMutationService<Metric, MetricRequest> {
    return {
      mutationFn: (variables: MetricRequest) => this.metricService.createMetric(variables),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.metrics.all })
      },
    }
  }
}

