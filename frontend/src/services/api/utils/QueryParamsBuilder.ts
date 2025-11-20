export interface QueryParams {
  [key: string]: string | number | undefined
}

export const buildQueryParams = (obj: QueryParams): string => {
  const params = new URLSearchParams()
  
  Object.entries(obj).forEach(([key, value]) => {
    if (value !== undefined && value !== null) {
      params.append(key, value.toString())
    }
  })
  
  return params.toString()
}

