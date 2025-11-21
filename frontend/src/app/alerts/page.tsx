import { AlertList } from '@/features/alerts/components/AlertList'
import { PageLayout } from '@/components/layout/PageLayout'

export default function AlertsPage() {
  return (
    <PageLayout title="Alertas" description="Gerencie e monitore alertas do sistema">
      <AlertList />
    </PageLayout>
  )
}

