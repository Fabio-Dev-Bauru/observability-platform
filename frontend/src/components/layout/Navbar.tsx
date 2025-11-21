'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { Button } from '@/components/ui/button'
import { cn } from '@/lib/utils'

const navItems = [
  { href: '/dashboard', label: 'Dashboard', icon: '📊' },
  { href: '/logs', label: 'Logs', icon: '📝' },
  { href: '/metrics', label: 'Métricas', icon: '📈' },
  { href: '/alerts', label: 'Alertas', icon: '🚨' },
]

export const Navbar = () => {
  const pathname = usePathname()

  return (
    <nav className="border-b bg-white shadow-sm">
      <div className="container mx-auto px-4">
        <div className="flex h-16 items-center justify-between">
          <div className="flex items-center gap-8">
            <Link href="/" className="text-xl font-bold text-primary-600">
              Observability Center
            </Link>
            <div className="flex gap-1">
              {navItems.map((item) => {
                const isActive = pathname === item.href || pathname.startsWith(item.href + '/')
                return (
                  <Button
                    key={item.href}
                    asChild
                    variant={isActive ? 'default' : 'ghost'}
                    className={cn(
                      'gap-2',
                      isActive && 'bg-primary-600 text-white hover:bg-primary-700'
                    )}
                  >
                    <Link href={item.href}>
                      <span>{item.icon}</span>
                      {item.label}
                    </Link>
                  </Button>
                )
              })}
            </div>
          </div>
        </div>
      </div>
    </nav>
  )
}

