import { Alert } from '@/types/alert'
import { IUseQuery } from './IUseQuery'

export interface IUseAlerts extends Omit<IUseQuery<Alert[]>, 'data' | 'refetch'> {
  alerts: Alert[]
  loadAlerts: () => Promise<void>
  resolveAlert: (id: string) => Promise<void>
}

