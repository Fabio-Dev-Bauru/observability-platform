# Observability Center - Frontend

Frontend application built with Next.js 14 and TypeScript.

## Prerequisites

- Node.js 18+
- npm or yarn

## Getting Started

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Start production server
npm start

# Run tests
npm test

# Run tests with coverage
npm run test:coverage
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Project Structure

- `src/app/` - Next.js App Router pages and layouts
- `src/components/` - Reusable UI components
- `src/features/` - Feature-based modules (logs, metrics, alerts)
- `src/services/` - API and WebSocket services
- `src/types/` - TypeScript type definitions
- `src/hooks/` - Custom React hooks
- `src/utils/` - Utility functions

## Architecture

The frontend follows a modular architecture:

- **Features**: Isolated feature modules with their own components, hooks, services, and types
- **Components**: Shared UI components organized by purpose
- **Services**: API clients and WebSocket connections
- **State Management**: Zustand for global state (minimal and modular)

