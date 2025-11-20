interface EmptyStateProps {
  message: string
}

export function EmptyState({ message }: EmptyStateProps) {
  return (
    <div className="flex justify-center items-center p-8">
      <div className="text-lg text-muted-foreground">{message}</div>
    </div>
  )
}

