export type ToastType = 'success' | 'error' | 'info' | 'warning'

export interface IToastService {
  show(message: string, type?: ToastType): void
  success(message: string): void
  error(message: string): void
  info(message: string): void
  warning(message: string): void
}

