import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { PageLayout } from '@/components/layout/PageLayout'

export default function Home() {
  return (
    <PageLayout>
      <div className="flex min-h-[60vh] flex-col items-center justify-center">
        <Card className="w-full max-w-2xl text-center">
          <CardHeader>
            <CardTitle className="text-4xl font-bold mb-4">
              Observability Center
            </CardTitle>
            <CardDescription className="text-lg">
              Plataforma de observabilidade full-stack para logs, métricas e eventos
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
                <Link href="/metrics">Métricas</Link>
              </Button>
              <Button asChild variant="outline" size="lg">
                <Link href="/alerts">Alertas</Link>
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </PageLayout>
  )
}

