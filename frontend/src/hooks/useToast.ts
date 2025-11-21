import { useMemo } from 'react'
import { IToastService } from '@/lib/toast/interfaces/IToastService'
import { toastService } from '@/lib/toast/implementations/ReactHotToastService'

export const useToast = (service: IToastService = toastService): IToastService => {
  return useMemo(() => service, [service])
}

