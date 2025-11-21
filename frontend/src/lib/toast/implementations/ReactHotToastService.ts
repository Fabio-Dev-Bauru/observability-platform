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
    
    switch (type) {
      case 'success':
        toast.success(message, options)
        break
      case 'error':
        toast.error(message, options)
        break
      case 'warning':
        toast(message, { ...options, icon: this.config.warningIcon })
        break
      case 'info':
        toast(message, { ...options, icon: this.config.infoIcon })
        break
    }
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

