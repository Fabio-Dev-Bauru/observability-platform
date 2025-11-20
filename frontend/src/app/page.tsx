import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24">
      <div className="z-10 max-w-5xl w-full items-center justify-between">
        <Card className="text-center">
          <CardHeader>
            <CardTitle className="text-4xl font-bold mb-4">
              Observability Center
            </CardTitle>
            <CardDescription className="text-lg">
              Full-stack observability platform for logs, metrics, and events
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex justify-center gap-4 flex-wrap">
              <Button asChild size="lg">
                <Link href="/dashboard">Dashboard</Link>
              </Button>
              <Button asChild variant="outline" size="lg">
                <Link href="/logs">Logs</Link>
              </Button>
              <Button asChild variant="outline" size="lg">
                <Link href="/metrics">Metrics</Link>
              </Button>
              <Button asChild variant="outline" size="lg">
                <Link href="/alerts">Alerts</Link>
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </main>
  )
}

