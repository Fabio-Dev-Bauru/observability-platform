'use client'

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
    <div className="bg-white p-6 rounded-lg shadow hover:shadow-md transition-shadow">
      <h2 className="text-lg font-semibold mb-2 text-gray-700">{title}</h2>
      <p className={`text-3xl font-bold ${colorClasses[color]}`}>
        {value}
      </p>
      {subtitle && (
        <p className="text-sm text-gray-500 mt-2">{subtitle}</p>
      )}
    </div>
  )
}

