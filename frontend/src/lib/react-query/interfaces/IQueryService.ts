import { QueryFunctionContext } from '@tanstack/react-query'

export interface IQueryService<TData> {
  queryKey: readonly unknown[]
  queryFn: (context: QueryFunctionContext) => Promise<TData>
}

export interface IMutationService<TData, TVariables> {
  mutationFn: (variables: TVariables) => Promise<TData>
  onSuccess?: (data: TData, variables: TVariables) => void
  onError?: (error: Error, variables: TVariables) => void
}

