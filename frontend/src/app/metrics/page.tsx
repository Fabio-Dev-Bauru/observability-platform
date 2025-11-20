import { MetricList } from '@/features/metrics/components/MetricList'

export default function MetricsPage() {
  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Metrics</h1>
      <MetricList />
    </div>
  )
}

