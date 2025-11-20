'use client'

import { Card, CardContent } from './card'

interface StatCardProps {
  title: string
  value: string | number
  subtitle?: string
  color?: 'primary' | 'red' | 'yellow' | 'green' | 'blue'
}

export function StatCard({ title, value, subtitle, color = 'primary' }: StatCardProps) {
  const colorClasses = {
    primary: 'text-primary-600',
    red: 'text-red-600',
    yellow: 'text-yellow-600',
    green: 'text-green-600',
    blue: 'text-blue-600',
  }

  return (
    <Card className="hover:shadow-md transition-shadow">
      <CardContent className="pt-6">
        <h2 className="text-base font-medium text-muted-foreground mb-2">{title}</h2>
        <p className={`text-3xl font-bold ${colorClasses[color]}`}>
          {value}
        </p>
        {subtitle && (
          <p className="text-sm text-muted-foreground mt-2">{subtitle}</p>
        )}
      </CardContent>
    </Card>
  )
}
