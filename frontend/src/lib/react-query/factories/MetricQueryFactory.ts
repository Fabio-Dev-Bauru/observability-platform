import { IQueryService, IMutationService } from '../interfaces/IQueryService'
import { IMetricService } from '@/services/api/interfaces/IMetricService'
import { Metric, MetricRequest, MetricFilters } from '@/types/metric'
import { queryKeys } from '../queryKeys'
import { queryClient } from '../queryClient'

export const createMetricListQuery = (
  metricService: IMetricService,
  filters?: MetricFilters
): IQueryService<Metric[], MetricFilters> => ({
  queryKey: queryKeys.metrics.list(filters),
  queryFn: () => metricService.getMetrics(filters),
})

export const createMetricCreateMutation = (
  metricService: IMetricService
): IMutationService<Metric, MetricRequest> => ({
  mutationFn: (variables: MetricRequest) => metricService.createMetric(variables),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: queryKeys.metrics.all })
  },
})
