interface LoadingStateProps {
  message?: string
}

export function LoadingState({ message = 'Loading...' }: LoadingStateProps) {
  return (
    <div className="flex justify-center items-center p-8">
      <div className="text-lg">{message}</div>
    </div>
  )
}

