# Observability Center

> Plataforma de observabilidade full-stack com arquitetura hexagonal, coletando e visualizando logs, métricas e eventos em tempo real.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Stack Tecnológica](#stack-tecnológica)
- [Arquitetura](#arquitetura)
  - [Backend - Hexagonal Architecture](#backend---hexagonal-architecture)
  - [Frontend - Estrutura Escalável](#frontend---estrutura-escalável)
- [Funcionalidades](#funcionalidades)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Setup e Instalação](#setup-e-instalação)
- [Testes](#testes)
- [CI/CD](#cicd)
- [Contribuindo](#contribuindo)
- [Diretrizes para IA](#diretrizes-para-ia)

## 🎯 Visão Geral

**Nome do projeto:** Observability Center

**Objetivo:** Plataforma de observabilidade full-stack com arquitetura limpa, modular e pronta para produção. O projeto demonstra senioridade técnica através de Hexagonal Architecture no backend e estrutura moderna e escalável no frontend.

## 🛠 Stack Tecnológica

### Backend
- **Runtime:** Java 17+
- **Framework:** Spring Boot
- **Arquitetura:** Hexagonal Architecture / Clean Architecture / DDD
- **Banco de Dados:** PostgreSQL / TimescaleDB (logs e métricas)
- **Cache:** Redis
- **Mensageria:** Kafka ou RabbitMQ (event-driven logs/metrics)

### Frontend
- **Framework:** Next.js 13+
- **Linguagem:** TypeScript
- **Estrutura:** Modular (features/modules, hooks, services, components)

### DevOps & Observabilidade
- **Visualização:** Grafana (dashboards + alertas)
- **Métricas:** Prometheus (opcional, para métricas do próprio Observability Center)
- **Containerização:** Docker + Docker Compose
- **CI/CD:** GitHub Actions (build, testes, lint, deploy)

### Segurança
- **Autenticação:** JWT / OAuth2
- **Autorização:** RBAC (Role-Based Access Control)

## 🏗 Arquitetura

### Backend - Hexagonal Architecture

A arquitetura hexagonal garante baixo acoplamento e alta testabilidade através de camadas bem definidas:

#### 1. Domain Layer (Núcleo)
- **Entities:** Objetos de domínio com identidade única
- **Value Objects:** Objetos imutáveis sem identidade
- **Aggregates:** Clusters de entidades tratadas como unidade
- **Domain Services:** Regras de negócio puras, sem dependências externas

#### 2. Application Layer (Casos de Uso)
- **Use Cases / Interactors:** Orquestração de comandos e eventos
- **Application Services:** Coordenação entre domínio e infraestrutura
- **DTOs:** Objetos de transferência de dados

#### 3. Adapters / Infrastructure (Implementações)
- **Repositórios:** Implementações para PostgreSQL / Redis
- **Event Publishers:** Integração com Kafka/RabbitMQ
- **REST Controllers:** Endpoints HTTP
- **WebSocket Adapters:** Comunicação em tempo real

#### 4. Port Interfaces (Contratos)
- Interfaces para banco de dados
- Interfaces para mensageria
- Interfaces para integrações externas
- **Objetivo:** Desacoplar o domínio da infraestrutura

#### Testes Backend
- **Unit tests:** Domain + Application layers
- **Integration tests:** Adapters e infraestrutura
- **Cobertura mínima:** 80%

### Backend - Funcionalidades

- ✅ Recebimento e persistência de logs/métricas
- ✅ Filtros avançados (nível, serviço, host, tags)
- ✅ Alertas configuráveis (thresholds, webhook/email/Slack)
- ✅ Health checks e métricas próprias
- ✅ WebSocket para atualização em tempo real

### Frontend - Estrutura Escalável

Estrutura modularizada seguindo princípios de Clean Architecture:

#### Estrutura de Pastas

```
src/
├── components/      # Componentes UI reutilizáveis
│   ├── ui/         # Botões, inputs, modais
│   ├── layout/     # Header, sidebar, footer
│   └── shared/     # Componentes compartilhados
├── features/        # Funcionalidades isoladas por domínio
│   ├── logs/       # Gestão de logs
│   ├── metrics/    # Gestão de métricas
│   └── alerts/     # Gestão de alertas
├── hooks/           # Custom React hooks
├── services/        # Chamadas API e WebSocket
├── pages/           # Rotas Next.js (App Router)
├── context/         # State global e providers
├── utils/           # Helpers e formatação
└── types/           # TypeScript types e interfaces
```

#### Frontend - Funcionalidades

- 📊 Dashboard interativo com gráficos e tabelas
- 🔍 Filtros dinâmicos (serviço, host, nível, período)
- 🚨 Página de alertas e histórico
- 🔐 Autenticação e RBAC
- ⚡ Atualização em tempo real via WebSocket
- 📱 Layout responsivo
- ♻️ Componentes reutilizáveis

#### Frontend - Boas Práticas

- ✅ **Type Safety:** TypeScript estrito em todo o projeto
- ✅ **Separação de Responsabilidades:** Components vs Services
- ✅ **State Management:** React Context ou Zustand (mínimo e modular)
- ✅ **Testes:** Jest + React Testing Library
- ✅ **Code Quality:** ESLint + Prettier
- ✅ **Performance:** Code splitting e lazy loading

## 📁 Estrutura de Pastas Completa

### Backend (Java/Spring Boot)

```
src/
├── main/
│   ├── java/com/observability/
│   │   ├── domain/              # Domain Layer
│   │   │   ├── entities/
│   │   │   ├── valueobjects/
│   │   │   ├── aggregates/
│   │   │   └── services/
│   │   ├── application/         # Application Layer
│   │   │   ├── usecases/
│   │   │   ├── services/
│   │   │   └── dtos/
│   │   ├── adapters/            # Adapters Layer
│   │   │   ├── input/
│   │   │   │   ├── rest/
│   │   │   │   └── websocket/
│   │   │   └── output/
│   │   │       ├── persistence/
│   │   │       └── messaging/
│   │   └── infrastructure/      # Infrastructure
│   │       ├── config/
│   │       ├── security/
│   │       └── monitoring/
│   └── resources/
└── test/                        # Testes espelhando src/
```

### Frontend (Next.js/TypeScript)

```
src/
├── app/                   # Next.js App Router
│   ├── (auth)/
│   ├── (dashboard)/
│   └── api/
├── components/
│   ├── ui/
│   ├── layout/
│   └── shared/
├── features/
│   ├── logs/
│   │   ├── components/
│   │   ├── hooks/
│   │   ├── services/
│   │   └── types/
│   ├── metrics/
│   └── alerts/
├── hooks/
├── services/
│   ├── api/
│   └── websocket/
├── context/
├── utils/
├── types/
└── __tests__/
```

## 🚀 Setup e Instalação

### Pré-requisitos

- Java 17+
- Node.js 18+
- Docker & Docker Compose
- PostgreSQL / TimescaleDB
- Redis
- Kafka ou RabbitMQ

### Backend

```bash
# Clone o repositório
git clone <repository-url>
cd observability-center/backend

# Build do projeto
./mvnw clean install

# Executar testes
./mvnw test

# Executar aplicação
./mvnw spring-boot:run
```

### Frontend

```bash
cd observability-center/frontend

# Instalar dependências
npm install

# Executar em desenvolvimento
npm run dev

# Build para produção
npm run build

# Executar testes
npm test
```

### Docker Compose

```bash
# Subir todos os serviços
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar serviços
docker-compose down
```

## 🧪 Testes

### Backend

```bash
# Executar todos os testes
./mvnw test

# Cobertura de testes
./mvnw test jacoco:report

# Testes de integração
./mvnw verify -P integration-tests
```

### Frontend

```bash
# Testes unitários
npm test

# Testes com cobertura
npm run test:coverage

# Testes E2E
npm run test:e2e
```

## 🔄 CI/CD

O projeto utiliza GitHub Actions para automação:

- ✅ Build automático em push/PR
- ✅ Execução de testes
- ✅ Análise de código (linting)
- ✅ Verificação de cobertura de testes
- ✅ Build de imagens Docker
- ✅ Deploy automático (staging/production)

## 🤝 Contribuindo

### Workflow de Desenvolvimento

1. Crie uma branch a partir de `develop`
2. Implemente suas mudanças
3. Escreva/atualize testes
4. Execute linting e testes localmente
5. Faça commit seguindo as convenções
6. Abra um Pull Request para `develop`

### Padrões de Código

- **Backend:** Seguir princípios SOLID e padrões de Clean Architecture
- **Frontend:** Utilizar TypeScript estrito, componentes funcionais
- **Commits:** Seguir Conventional Commits
- **Cobertura:** Manter mínimo de 80% de cobertura de testes

## 🤖 Diretrizes para IA

### Regras de Commits e Comentários

**⚠️ IMPORTANTE: Todos os commits e comentários de código devem ser escritos em INGLÊS.**

#### Formato de Commits (Conventional Commits)

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `docs`: Documentação
- `style`: Formatação, ponto e vírgula, etc
- `refactor`: Refatoração de código
- `test`: Adição ou correção de testes
- `chore`: Tarefas de manutenção

**Exemplos:**

```bash
# ✅ CORRETO (Inglês)
git commit -m "feat(logs): add real-time log streaming via WebSocket"
git commit -m "fix(alerts): resolve threshold validation issue"
git commit -m "docs(readme): update setup instructions"

# ❌ INCORRETO (Português)
git commit -m "feat(logs): adiciona streaming de logs em tempo real"
```

#### Comentários de Código

**✅ CORRETO:**

```java
/**
 * Processes incoming log entries and publishes them to the event stream.
 * 
 * @param logEntry The log entry to be processed
 * @return ProcessedLog The processed log with enriched metadata
 * @throws InvalidLogException if the log entry is malformed
 */
public ProcessedLog processLog(LogEntry logEntry) {
    // Validate log entry format
    validateLogFormat(logEntry);
    
    // Enrich with metadata
    return enrichMetadata(logEntry);
}
```

```typescript
/**
 * Custom hook for managing WebSocket connections to the log stream.
 * 
 * @param url - WebSocket endpoint URL
 * @param options - Connection options
 * @returns WebSocket state and control methods
 */
export const useLogStream = (url: string, options?: WebSocketOptions) => {
    // Initialize connection state
    const [connected, setConnected] = useState(false);
    
    // Handle connection lifecycle
    useEffect(() => {
        // Connect to WebSocket server
        const ws = new WebSocket(url);
        
        // ...
    }, [url]);
};
```

**❌ INCORRETO:**

```java
// Processa a entrada de log e publica no stream de eventos
public ProcessedLog processLog(LogEntry logEntry) {
    // Valida o formato do log
    validateLogFormat(logEntry);
    // ...
}
```

#### Nomenclatura

- **Classes/Interfaces:** PascalCase em inglês
  - ✅ `LogProcessor`, `MetricRepository`, `AlertService`
  - ❌ `ProcessadorLog`, `RepositorioMetrica`

- **Métodos/Funções:** camelCase em inglês
  - ✅ `processLogEntry()`, `calculateMetrics()`, `sendAlert()`
  - ❌ `processarEntradaLog()`, `calcularMetricas()`

- **Variáveis:** camelCase em inglês
  - ✅ `logLevel`, `metricValue`, `alertThreshold`
  - ❌ `nivelLog`, `valorMetrica`

- **Constantes:** UPPER_SNAKE_CASE em inglês
  - ✅ `MAX_RETRY_ATTEMPTS`, `DEFAULT_TIMEOUT`
  - ❌ `TENTATIVAS_MAXIMAS`, `TEMPO_PADRAO`

#### Pull Requests

- **Título:** Em inglês, descritivo
- **Descrição:** Em inglês, explicando mudanças e motivação
- **Labels:** Utilizar labels em inglês

**Exemplo de PR:**

```markdown
## Description
Implements real-time log streaming using WebSocket connections.

## Changes
- Added WebSocket adapter in backend
- Created useLogStream hook in frontend
- Implemented connection management and reconnection logic

## Testing
- Unit tests for WebSocket adapter
- Integration tests for log streaming
- Manual testing in development environment

## Related Issues
Closes #123
```

#### Documentação de API

Toda documentação de API (OpenAPI/Swagger, JSDoc, JavaDoc) deve estar em inglês:

```yaml
# ✅ CORRETO
/api/logs:
  get:
    summary: Retrieve paginated logs
    description: Fetches logs with optional filtering by level, service, and time range
    parameters:
      - name: level
        description: Log level filter (ERROR, WARN, INFO, DEBUG)
```

#### Mensagens de Erro

Mensagens de erro em logs e exceptions devem estar em inglês:

```java
// ✅ CORRETO
throw new InvalidLogException("Log entry must contain a timestamp");

// ❌ INCORRETO
throw new InvalidLogException("Entrada de log deve conter timestamp");
```

Lembre-se que a cada fase do projeto, vamos fazer um commit, ok?



**Desenvolvido com foco em qualidade, escalabilidade e boas práticas de engenharia de software.**