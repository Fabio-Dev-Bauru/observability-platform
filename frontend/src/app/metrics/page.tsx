import { MetricList } from '@/features/metrics/components/MetricList'
import { PageLayout } from '@/components/layout/PageLayout'

export default function MetricsPage() {
  return (
    <PageLayout title="Métricas" description="Visualize e monitore métricas do sistema">
      <MetricList />
    </PageLayout>
  )
}

