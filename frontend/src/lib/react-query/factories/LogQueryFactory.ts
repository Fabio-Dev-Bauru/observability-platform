import { IQueryService, IMutationService } from '../interfaces/IQueryService'
import { ILogService } from '@/services/api/interfaces/ILogService'
import { LogEntry, LogEntryRequest, LogFilters } from '@/types/log'
import { queryKeys } from '../queryKeys'
import { queryClient } from '../queryClient'

export const createLogListQuery = (
  logService: ILogService,
  filters?: LogFilters
): IQueryService<LogEntry[], LogFilters> => ({
  queryKey: queryKeys.logs.list(filters),
  queryFn: () => logService.getLogs(filters),
})

export const createLogCreateMutation = (
  logService: ILogService
): IMutationService<LogEntry, LogEntryRequest> => ({
  mutationFn: (variables: LogEntryRequest) => logService.createLog(variables),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: queryKeys.logs.all })
  },
})
