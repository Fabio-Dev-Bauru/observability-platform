import { IQueryService, IMutationService } from '../interfaces/IQueryService'
import { ILogService } from '@/services/api/interfaces/ILogService'
import { LogEntry, LogEntryRequest, LogFilters } from '@/types/log'
import { queryKeys } from '../queryKeys'
import { queryClient } from '../queryClient'

export class LogQueryFactory {
  constructor(private logService: ILogService) {}

  createListQuery(filters?: LogFilters): IQueryService<LogEntry[], LogFilters> {
    return {
      queryKey: queryKeys.logs.list(filters),
      queryFn: () => this.logService.getLogs(filters),
    }
  }

  createCreateMutation(): IMutationService<LogEntry, LogEntryRequest> {
    return {
      mutationFn: (variables: LogEntryRequest) => this.logService.createLog(variables),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.logs.all })
      },
    }
  }
}

