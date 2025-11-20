import { Alert } from '@/types/alert'

export interface IUseAlerts {
  alerts: Alert[]
  loading: boolean
  error: string | null
  loadAlerts: () => Promise<void>
  resolveAlert: (id: string) => Promise<void>
}

