import { format } from 'date-fns'

export const formatDateTime = (date: Date | string): string =>
  format(new Date(date), 'yyyy-MM-dd HH:mm:ss')

export const formatDate = (date: Date | string): string =>
  format(new Date(date), 'yyyy-MM-dd')

export const formatTime = (date: Date | string): string =>
  format(new Date(date), 'HH:mm:ss')

