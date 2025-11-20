import { Button } from '@/components/ui/button'

interface ErrorStateProps {
  message: string
  onRetry?: () => void
}

export function ErrorState({ message, onRetry }: ErrorStateProps) {
  return (
    <div className="flex flex-col items-center p-8">
      <div className="text-red-600 mb-4">Error: {message}</div>
      {onRetry && <Button onClick={onRetry}>Retry</Button>}
    </div>
  )
}

