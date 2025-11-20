export interface IQueryService<TData, TVariables = void> {
  queryKey: readonly unknown[]
  queryFn: (variables?: TVariables) => Promise<TData>
}

export interface IMutationService<TData, TVariables> {
  mutationFn: (variables: TVariables) => Promise<TData>
  onSuccess?: (data: TData, variables: TVariables) => void
  onError?: (error: Error, variables: TVariables) => void
}

