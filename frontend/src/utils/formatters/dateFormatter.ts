import { format } from 'date-fns'

export class DateFormatter {
  static formatDateTime(date: Date | string): string {
    return format(new Date(date), 'yyyy-MM-dd HH:mm:ss')
  }

  static formatDate(date: Date | string): string {
    return format(new Date(date), 'yyyy-MM-dd')
  }

  static formatTime(date: Date | string): string {
    return format(new Date(date), 'HH:mm:ss')
  }
}

