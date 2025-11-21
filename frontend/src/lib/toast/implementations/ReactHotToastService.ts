import toast, { ToastOptions } from 'react-hot-toast'
import { IToastService, ToastType } from '../interfaces/IToastService'
import { ToastConfig, defaultToastConfig } from '../config/ToastConfig'

class ReactHotToastService implements IToastService {
  private config: ToastConfig

  constructor(config: ToastConfig = defaultToastConfig) {
    this.config = { ...defaultToastConfig, ...config }
  }

  private getToastOptions(type: ToastType): ToastOptions {
    const baseOptions: ToastOptions = {
      duration: this.config.duration,
      position: this.config.position,
    }

    switch (type) {
      case 'success':
        return { ...baseOptions, icon: this.config.successIcon }
      case 'error':
        return { ...baseOptions, icon: this.config.errorIcon }
      case 'warning':
        return { ...baseOptions, icon: this.config.warningIcon }
      case 'info':
        return { ...baseOptions, icon: this.config.infoIcon }
      default:
        return baseOptions
    }
  }

  show(message: string, type: ToastType = 'info'): void {
    const options = this.getToastOptions(type)
    
    const toastFn = {
      success: toast.success,
      error: toast.error,
      warning: toast,
      info: toast,
    }[type]

    toastFn(message, options)
  }

  success(message: string): void {
    this.show(message, 'success')
  }

  error(message: string): void {
    this.show(message, 'error')
  }

  info(message: string): void {
    this.show(message, 'info')
  }

  warning(message: string): void {
    this.show(message, 'warning')
  }
}

export const toastService = new ReactHotToastService()

