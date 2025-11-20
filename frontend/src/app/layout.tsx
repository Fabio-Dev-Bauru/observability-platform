import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import { QueryProvider } from '@/lib/react-query/QueryProvider'
import './globals.css'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Observability Center',
  description: 'Full-stack observability platform for logs, metrics, and events',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="pt-BR">
      <body className={inter.className}>
        <QueryProvider>{children}</QueryProvider>
      </body>
    </html>
  )
}

