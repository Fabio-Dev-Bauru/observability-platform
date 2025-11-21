import { useEffect, useRef, useState } from 'react'
import { IWebSocketClient, WebSocketTopic } from '@/lib/websocket/interfaces/IWebSocketClient'
import { webSocketClient } from '@/lib/websocket/implementations/StompWebSocketClient'

export const useWebSocket = <T = unknown>(
  topic: WebSocketTopic,
  onMessage: (data: T) => void,
  client: IWebSocketClient = webSocketClient
) => {
  const [isConnected, setIsConnected] = useState(false)
  const onMessageRef = useRef(onMessage)

  useEffect(() => {
    onMessageRef.current = onMessage
  }, [onMessage])

  useEffect(() => {
    let unsubscribe: (() => void) | null = null

    const connectAndSubscribe = async () => {
      try {
        await client.connect()
        setIsConnected(true)

        unsubscribe = client.subscribe<T>(topic, (data) => {
          onMessageRef.current(data)
        })
      } catch (error) {
        console.error(`Failed to connect to WebSocket topic ${topic}:`, error)
        setIsConnected(false)
      }
    }

    connectAndSubscribe()

    return () => {
      if (unsubscribe) {
        unsubscribe()
      }
    }
  }, [topic, client])

  return { isConnected }
}

