export interface IUseQuery<TData> {
  data: TData
  loading: boolean
  error: string | null
  refetch: () => Promise<void>
}

