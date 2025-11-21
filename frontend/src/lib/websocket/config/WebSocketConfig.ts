export interface WebSocketConfig {
  url: string
  reconnectDelay?: number
  maxReconnectAttempts?: number
}

export const defaultWebSocketConfig: WebSocketConfig = {
  url: process.env.NEXT_PUBLIC_WS_URL || 'http://localhost:8080/ws',
  reconnectDelay: 3000,
  maxReconnectAttempts: 5,
}

