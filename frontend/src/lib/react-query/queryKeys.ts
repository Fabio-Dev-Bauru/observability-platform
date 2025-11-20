export const queryKeys = {
  logs: {
    all: ['logs'] as const,
    lists: () => [...queryKeys.logs.all, 'list'] as const,
    list: (filters?: unknown) => [...queryKeys.logs.lists(), filters] as const,
    detail: (id: string) => [...queryKeys.logs.all, 'detail', id] as const,
  },
  metrics: {
    all: ['metrics'] as const,
    lists: () => [...queryKeys.metrics.all, 'list'] as const,
    list: (filters?: unknown) => [...queryKeys.metrics.lists(), filters] as const,
    detail: (id: string) => [...queryKeys.metrics.all, 'detail', id] as const,
  },
  alerts: {
    all: ['alerts'] as const,
    lists: () => [...queryKeys.alerts.all, 'list'] as const,
    list: (filters?: unknown) => [...queryKeys.alerts.lists(), filters] as const,
    active: () => [...queryKeys.alerts.all, 'active'] as const,
    detail: (id: string) => [...queryKeys.alerts.all, 'detail', id] as const,
  },
  dashboard: {
    all: ['dashboard'] as const,
    stats: () => [...queryKeys.dashboard.all, 'stats'] as const,
  },
} as const

