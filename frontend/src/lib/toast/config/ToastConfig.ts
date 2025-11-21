export interface ToastConfig {
  duration?: number
  position?: 'top-left' | 'top-center' | 'top-right' | 'bottom-left' | 'bottom-center' | 'bottom-right'
  successIcon?: string
  errorIcon?: string
  warningIcon?: string
  infoIcon?: string
}

export const defaultToastConfig: ToastConfig = {
  duration: 4000,
  position: 'top-right',
  successIcon: '✅',
  errorIcon: '❌',
  warningIcon: '⚠️',
  infoIcon: 'ℹ️',
}

