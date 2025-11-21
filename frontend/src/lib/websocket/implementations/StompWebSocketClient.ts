import { Client, IMessage } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import { IWebSocketClient, WebSocketTopic } from '../interfaces/IWebSocketClient'
import { WebSocketConfig, defaultWebSocketConfig } from '../config/WebSocketConfig'

class StompWebSocketClient implements IWebSocketClient {
  private client: Client | null = null
  private config: WebSocketConfig
  private subscriptions: Map<WebSocketTopic, Set<(data: unknown) => void>> = new Map()
  private reconnectAttempts = 0

  constructor(config: WebSocketConfig = defaultWebSocketConfig) {
    this.config = { ...defaultWebSocketConfig, ...config }
  }

  connect(): Promise<void> {
    return new Promise((resolve, reject) => {
      if (this.client?.connected) {
        resolve()
        return
      }

      this.client = new Client({
        webSocketFactory: () => new SockJS(this.config.url) as unknown as WebSocket,
        reconnectDelay: this.config.reconnectDelay,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: () => {
          this.reconnectAttempts = 0
          this.resubscribeAll()
          resolve()
        },
        onStompError: (frame) => {
          console.error('WebSocket STOMP error:', frame)
          reject(new Error(frame.headers['message'] || 'WebSocket connection failed'))
        },
        onWebSocketClose: () => {
          this.handleReconnect()
        },
        onDisconnect: () => {
          this.subscriptions.clear()
        },
      })

      this.client.activate()
    })
  }

  disconnect(): void {
    if (this.client) {
      this.client.deactivate()
      this.client = null
      this.subscriptions.clear()
    }
  }

  subscribe<T>(topic: WebSocketTopic, callback: (data: T) => void): () => void {
    if (!this.subscriptions.has(topic)) {
      this.subscriptions.set(topic, new Set())
    }

    const callbacks = this.subscriptions.get(topic)!
    callbacks.add(callback as (data: unknown) => void)

    if (this.client?.connected) {
      this.subscribeToTopic(topic)
    }

    return () => {
      const topicCallbacks = this.subscriptions.get(topic)
      if (topicCallbacks) {
        topicCallbacks.delete(callback as (data: unknown) => void)
        if (topicCallbacks.size === 0) {
          this.subscriptions.delete(topic)
        }
      }
    }
  }

  isConnected(): boolean {
    return this.client?.connected ?? false
  }

  private subscribeToTopic(topic: WebSocketTopic): void {
    if (!this.client?.connected) return

    this.client.subscribe(topic, (message: IMessage) => {
      try {
        const data = JSON.parse(message.body)
        const callbacks = this.subscriptions.get(topic)
        if (callbacks) {
          callbacks.forEach((callback) => callback(data))
        }
      } catch (error) {
        console.error(`Error parsing WebSocket message from ${topic}:`, error)
      }
    })
  }

  private resubscribeAll(): void {
    this.subscriptions.forEach((_, topic) => {
      this.subscribeToTopic(topic)
    })
  }

  private handleReconnect(): void {
    if (this.reconnectAttempts >= (this.config.maxReconnectAttempts || 5)) {
      console.error('Max reconnection attempts reached')
      return
    }

    this.reconnectAttempts++
    setTimeout(() => {
      this.connect().catch((error) => {
        console.error('Reconnection failed:', error)
      })
    }, this.config.reconnectDelay)
  }
}

export const webSocketClient = new StompWebSocketClient()

