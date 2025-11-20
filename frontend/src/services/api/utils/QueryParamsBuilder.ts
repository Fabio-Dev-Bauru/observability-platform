export interface QueryParams {
  [key: string]: string | number | undefined
}

export class QueryParamsBuilder {
  private params: URLSearchParams

  constructor() {
    this.params = new URLSearchParams()
  }

  append(key: string, value: string | number | undefined): this {
    if (value !== undefined && value !== null) {
      this.params.append(key, value.toString())
    }
    return this
  }

  build(): string {
    return this.params.toString()
  }

  static fromObject(obj: QueryParams): string {
    const builder = new QueryParamsBuilder()
    Object.entries(obj).forEach(([key, value]) => {
      builder.append(key, value)
    })
    return builder.build()
  }
}

