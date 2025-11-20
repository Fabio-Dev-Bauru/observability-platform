# Como Funciona o Observability Center

## 🎯 O Que É Este Projeto?

O **Observability Center** é uma plataforma completa para monitorar sistemas em tempo real. Imagine que você tem vários serviços rodando (aplicações web, APIs, bancos de dados) e precisa saber:
- O que está acontecendo agora?
- Há algum erro?
- O sistema está lento?
- Algo precisa de atenção urgente?

Esta plataforma coleta, armazena e exibe essas informações de forma organizada e em tempo real.

---

## 🏗 Arquitetura Geral

O projeto é dividido em duas partes principais:

```
┌─────────────────┐         ┌─────────────────┐
│    Frontend     │ ◄─────► │     Backend     │
│   (Next.js)     │   API   │  (Spring Boot)  │
└─────────────────┘         └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │   PostgreSQL    │
                            │  (TimescaleDB)  │
                            └─────────────────┘
```

### Backend (Spring Boot)
- **Responsabilidade**: Receber, processar e armazenar dados
- **Linguagem**: Java 17
- **Arquitetura**: Hexagonal (Clean Architecture)

### Frontend (Next.js)
- **Responsabilidade**: Exibir dados de forma visual e interativa
- **Linguagem**: TypeScript
- **Framework**: Next.js 14

### Banco de Dados
- **PostgreSQL com TimescaleDB**: Armazena logs, métricas e alertas
- **Redis**: Cache para melhorar performance
- **Kafka**: Mensageria para eventos em tempo real (opcional)

---

## 📊 Os Três Tipos de Dados

### 1. **Logs** (Registros)
São mensagens que os sistemas geram durante a execução.

**Exemplo:**
```
[INFO] 2025-11-20 19:00:00 - Usuário fez login
[ERROR] 2025-11-20 19:01:00 - Falha ao conectar no banco
[WARN] 2025-11-20 19:02:00 - Memória alta: 85%
```

**Níveis de Log:**
- `TRACE` / `DEBUG`: Informações detalhadas para desenvolvedores
- `INFO`: Informações normais de operação
- `WARN`: Avisos (algo pode estar errado)
- `ERROR` / `FATAL`: Erros que precisam atenção

### 2. **Métricas** (Medições)
São números que representam o estado do sistema.

**Exemplos:**
- CPU: 75%
- Memória: 8GB usados
- Requisições por segundo: 150
- Tempo de resposta: 200ms

**Tipos de Métricas:**
- `COUNTER`: Valores que só aumentam (ex: total de requisições)
- `GAUGE`: Valores que sobem e descem (ex: CPU, memória)
- `HISTOGRAM`: Distribuição de valores (ex: tempo de resposta)
- `SUMMARY`: Estatísticas resumidas

### 3. **Alertas** (Avisos)
São notificações quando algo precisa de atenção.

**Exemplo:**
```
🚨 ALERTA CRÍTICO
Nome: CPU acima de 90%
Serviço: api-service
Severidade: CRITICAL
Status: ATIVO
```

**Níveis de Severidade:**
- `LOW`: Baixa prioridade
- `MEDIUM`: Média prioridade
- `HIGH`: Alta prioridade
- `CRITICAL`: Crítico - ação imediata necessária

---

## 🔄 Como Funciona o Fluxo de Dados

### Cenário 1: Um Serviço Envia um Log

```
1. Serviço Externo
   └─> Envia log via HTTP POST
       POST /api/v1/logs
       {
         "level": "ERROR",
         "message": "Falha na conexão",
         "service": "api-service"
       }

2. Backend (Spring Boot)
   ├─> Recebe no LogController
   ├─> Valida os dados (CreateLogEntryUseCase)
   ├─> Salva no banco (LogRepository)
   └─> Publica evento (EventPublisher)
       ├─> Envia para Kafka (opcional)
       └─> Envia via WebSocket para frontend

3. Frontend (Next.js)
   └─> Recebe via WebSocket
       └─> Atualiza a lista de logs automaticamente
```

### Cenário 2: Visualizando Dados no Frontend

```
1. Usuário acessa /logs no navegador

2. Frontend
   └─> Faz requisição GET /api/v1/logs
       └─> Backend busca no banco
           └─> Retorna lista de logs
               └─> Frontend exibe na tela

3. WebSocket mantém conexão aberta
   └─> Novos logs aparecem automaticamente
       (sem precisar atualizar a página)
```

### Cenário 3: Criando um Alerta

```
1. Sistema detecta problema
   └─> Envia métrica: CPU = 95%

2. Backend recebe métrica
   └─> (Futuro: regra de negócio verifica)
       └─> Se CPU > 90% → Cria alerta

3. Alerta é criado
   ├─> Salvo no banco
   ├─> Enviado via WebSocket
   └─> Aparece no frontend em tempo real
```

---

## 🏛 Arquitetura Hexagonal (Backend)

A arquitetura hexagonal separa o código em camadas bem definidas:

```
┌─────────────────────────────────────────┐
│         ADAPTERS (Adaptadores)          │
│  ┌──────────┐  ┌──────────────────┐    │
│  │  REST    │  │   WebSocket      │    │
│  │Controller│  │   Controller     │    │
│  └──────────┘  └──────────────────┘    │
│         │              │                │
│         ▼              ▼                │
│  ┌──────────────────────────────┐      │
│  │   APPLICATION (Aplicação)     │      │
│  │  - Use Cases (Casos de Uso)  │      │
│  │  - DTOs (Objetos de Dados)   │      │
│  │  - Mappers (Conversores)     │      │
│  └──────────────────────────────┘      │
│         │              │                │
│         ▼              ▼                │
│  ┌──────────────────────────────┐      │
│  │    DOMAIN (Domínio)           │      │
│  │  - Entities (Entidades)       │      │
│  │  - Value Objects              │      │
│  │  - Ports (Interfaces)         │      │
│  └──────────────────────────────┘      │
│         │              │                │
│         ▼              ▼                │
│  ┌──────────────────────────────┐      │
│  │  ADAPTERS (Infraestrutura)    │      │
│  │  - Repository (Banco)        │      │
│  │  - EventPublisher (Kafka)    │      │
│  └──────────────────────────────┘      │
└─────────────────────────────────────────┘
```

### Por Que Esta Arquitetura?

**Vantagens:**
1. **Desacoplamento**: Cada camada não depende diretamente da outra
2. **Testabilidade**: Fácil de testar cada parte isoladamente
3. **Manutenibilidade**: Mudanças em uma camada não afetam outras
4. **Flexibilidade**: Pode trocar banco de dados sem mudar regras de negócio

**Exemplo Prático:**
- Se quiser trocar PostgreSQL por MongoDB, só muda a camada de Repository
- As regras de negócio (Domain) continuam iguais
- Os controllers (REST) continuam iguais

---

## 🔌 Como os Componentes Se Comunicam

### 1. **REST API** (Requisições HTTP)

**Frontend → Backend:**
```typescript
// Frontend faz requisição
const logs = await logService.getLogs({
  level: 'ERROR',
  service: 'api-service'
});
```

**Backend processa:**
```java
// Controller recebe
@GetMapping
public List<LogEntryResponse> getLogs(...) {
    // Use Case executa lógica
    List<LogEntry> logs = getLogsUseCase.execute(...);
    // Retorna DTOs
    return logs.map(mapper::toResponse);
}
```

### 2. **WebSocket** (Tempo Real)

**Conexão:**
```
Frontend conecta: ws://localhost:8080/ws
Backend aceita conexão
```

**Quando novo log é criado:**
```
1. Backend salva log no banco
2. EventPublisher.publishLogEvent(log)
3. WebSocket envia para /topic/logs
4. Frontend recebe automaticamente
5. Lista atualiza sem refresh
```

### 3. **Banco de Dados**

**Estrutura:**
```
logs
├── id
├── level (ERROR, INFO, etc)
├── message
├── service
├── timestamp
└── ...

metrics
├── id
├── name (cpu.usage)
├── value (75.5)
├── type (GAUGE, COUNTER)
└── ...

alerts
├── id
├── name
├── severity (CRITICAL, HIGH)
├── status (ACTIVE, RESOLVED)
└── ...
```

---

## 🚀 Como Usar o Sistema

### Para Desenvolvedores (Enviar Dados)

**Enviar um Log:**
```bash
curl -X POST http://localhost:8080/api/v1/logs \
  -H "Content-Type: application/json" \
  -d '{
    "level": "INFO",
    "message": "Processo iniciado",
    "service": "meu-servico"
  }'
```

**Enviar uma Métrica:**
```bash
curl -X POST http://localhost:8080/api/v1/metrics \
  -H "Content-Type: application/json" \
  -d '{
    "name": "cpu.usage",
    "value": 75.5,
    "type": "GAUGE",
    "service": "meu-servico"
  }'
```

**Criar um Alerta:**
```bash
curl -X POST http://localhost:8080/api/v1/alerts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "CPU Alto",
    "severity": "HIGH",
    "service": "meu-servico"
  }'
```

### Para Usuários (Visualizar Dados)

1. **Acesse o Frontend**: http://localhost:3000
2. **Navegue pelas páginas**:
   - `/logs` - Ver todos os logs
   - `/metrics` - Ver métricas
   - `/alerts` - Ver alertas
   - `/dashboard` - Visão geral

3. **Filtros disponíveis**:
   - Por serviço
   - Por nível (logs)
   - Por severidade (alertas)
   - Por período de tempo

4. **Tempo Real**:
   - Novos dados aparecem automaticamente
   - Não precisa atualizar a página

---

## 🎨 Interface do Usuário

### Página de Logs
```
┌─────────────────────────────────────┐
│  Logs                               │
├─────────────────────────────────────┤
│ [ERROR] api-service @ server-01     │
│ Falha na conexão com banco          │
│ 2025-11-20 19:00:00                 │
├─────────────────────────────────────┤
│ [INFO] api-service @ server-01      │
│ Requisição processada com sucesso   │
│ 2025-11-20 19:00:05                 │
└─────────────────────────────────────┘
```

### Página de Métricas
```
┌─────────────────────────────────────┐
│  Metrics                            │
├─────────────────────────────────────┤
│ [GAUGE] cpu.usage @ api-service     │
│ 75.5                                 │
│ 2025-11-20 19:00:00                 │
├─────────────────────────────────────┤
│ [COUNTER] requests.total            │
│ 1500                                │
│ 2025-11-20 19:00:00                 │
└─────────────────────────────────────┘
```

### Página de Alertas
```
┌─────────────────────────────────────┐
│  Alerts                             │
├─────────────────────────────────────┤
│ [CRITICAL] [ACTIVE] CPU Alto       │
│ CPU acima de 90%                    │
│ [Resolver] 2025-11-20 19:00:00     │
├─────────────────────────────────────┤
│ [HIGH] [RESOLVED] Memória Alta     │
│ Memória acima de 85%                │
│ 2025-11-20 18:55:00                │
└─────────────────────────────────────┘
```

---

## 🔧 Tecnologias Utilizadas

### Backend
- **Java 17**: Linguagem de programação
- **Spring Boot 3.2**: Framework
- **PostgreSQL + TimescaleDB**: Banco de dados
- **Redis**: Cache
- **Kafka**: Mensageria (opcional)
- **WebSocket**: Comunicação em tempo real

### Frontend
- **Next.js 14**: Framework React
- **TypeScript**: Tipagem estática
- **Tailwind CSS**: Estilização
- **Axios**: Requisições HTTP
- **WebSocket Client**: Conexão em tempo real

### DevOps
- **Docker Compose**: Orquestração de serviços
- **GitHub Actions**: CI/CD
- **Maven**: Build do backend
- **npm**: Gerenciamento de dependências frontend

---

## 📈 Fluxo Completo de Exemplo

**Cenário: Sistema detecta problema de CPU**

```
1. Serviço monitora CPU
   └─> CPU = 95%

2. Envia métrica para backend
   POST /api/v1/metrics
   { "name": "cpu.usage", "value": 95, "type": "GAUGE" }

3. Backend processa
   ├─> Valida dados
   ├─> Salva no banco
   └─> Publica evento

4. WebSocket envia para frontend
   └─> Frontend atualiza lista de métricas

5. (Futuro) Regra de negócio detecta
   └─> Se CPU > 90% → Cria alerta

6. Alerta é criado
   ├─> Salvo no banco
   ├─> Enviado via WebSocket
   └─> Aparece na página de alertas

7. Usuário vê alerta
   └─> Clica em "Resolver"
       └─> Alerta marcado como RESOLVED
```

---

## 🎯 Resumo Simples

**O que o projeto faz:**
1. **Coleta** dados de sistemas (logs, métricas, alertas)
2. **Armazena** no banco de dados
3. **Exibe** em uma interface web bonita
4. **Atualiza** em tempo real via WebSocket

**Por que é útil:**
- Ver o que está acontecendo nos sistemas
- Detectar problemas rapidamente
- Monitorar performance
- Histórico de eventos

**Como funciona:**
- Backend recebe dados via API REST
- Salva no banco PostgreSQL
- Frontend busca e exibe
- WebSocket mantém atualização automática

---

## 🚀 Próximos Passos (Futuro)

1. **Gráficos**: Visualizar métricas em gráficos
2. **Alertas Automáticos**: Criar alertas baseados em regras
3. **Autenticação**: Login e permissões
4. **Dashboards Customizáveis**: Usuário cria seus próprios dashboards
5. **Exportação**: Exportar dados para análise

---

**Em resumo**: É como um "painel de controle" para sistemas, onde você vê tudo que está acontecendo em tempo real! 🎉

