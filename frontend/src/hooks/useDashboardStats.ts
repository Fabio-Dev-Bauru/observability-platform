'use client'

import { useEffect, useState } from 'react'
import { DashboardStats } from '@/types/dashboard'
import { dashboardService } from '@/services/api/dashboardService'

export function useDashboardStats() {
  const [stats, setStats] = useState<DashboardStats | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const loadStats = async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await dashboardService.getStats()
      setStats(data)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load dashboard stats')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadStats()
    const interval = setInterval(loadStats, 30000)
    return () => clearInterval(interval)
  }, [])

  return { stats, loading, error, refetch: loadStats }
}

