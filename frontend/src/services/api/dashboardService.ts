import axios from 'axios'
import { DashboardStats } from '@/types/dashboard'

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080'

const apiClient = axios.create({
  baseURL: `${API_URL}/api/v1`,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const dashboardService = {
  async getStats(): Promise<DashboardStats> {
    const response = await apiClient.get<DashboardStats>('/dashboard/stats')
    return response.data
  },
}

