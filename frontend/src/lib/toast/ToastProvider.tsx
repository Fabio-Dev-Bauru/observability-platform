'use client'

import { Toaster } from 'react-hot-toast'
import { defaultToastConfig } from './config/ToastConfig'

export const ToastProvider = () => {
  return (
    <Toaster
      position={defaultToastConfig.position}
      toastOptions={{
        duration: defaultToastConfig.duration,
        style: {
          background: '#fff',
          color: '#333',
          boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
          borderRadius: '8px',
          padding: '12px 16px',
        },
        success: {
          iconTheme: {
            primary: '#10b981',
            secondary: '#fff',
          },
        },
        error: {
          iconTheme: {
            primary: '#ef4444',
            secondary: '#fff',
          },
        },
        warning: {
          iconTheme: {
            primary: '#f59e0b',
            secondary: '#fff',
          },
        },
        info: {
          iconTheme: {
            primary: '#3b82f6',
            secondary: '#fff',
          },
        },
      }}
    />
  )
}

