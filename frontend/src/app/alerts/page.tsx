import { AlertList } from '@/features/alerts/components/AlertList'

export default function AlertsPage() {
  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Alerts</h1>
      <AlertList />
    </div>
  )
}

