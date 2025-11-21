export type WebSocketTopic = '/topic/logs' | '/topic/metrics' | '/topic/alerts'

export interface WebSocketMessage<T = unknown> {
  topic: WebSocketTopic
  data: T
}

export interface IWebSocketClient {
  connect(): Promise<void>
  disconnect(): void
  subscribe<T>(topic: WebSocketTopic, callback: (data: T) => void): () => void
  isConnected(): boolean
}

