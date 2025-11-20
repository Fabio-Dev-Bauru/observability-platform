# Observability Center

> Plataforma de observabilidade full-stack com arquitetura hexagonal, coletando e visualizando logs, métricas e eventos em tempo real.

## 🚀 Início Rápido

### Pré-requisitos

- Java 17+
- Node.js 18+
- Docker & Docker Compose
- Maven 3.8+

### Setup Completo

1. **Clone o repositório**
```bash
git clone <repository-url>
cd observability
```

2. **Inicie os serviços com Docker Compose**
```bash
docker-compose up -d
```

3. **Backend**
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

4. **Frontend**
```bash
cd frontend
npm install
npm run dev
```

Acesse:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- API Docs: http://localhost:8080/swagger-ui.html

## 📁 Estrutura do Projeto

```
observability/
├── backend/          # Spring Boot com arquitetura hexagonal
├── frontend/         # Next.js com TypeScript
├── docker-compose.yml
└── .github/workflows/ # CI/CD
```

## 🏗 Arquitetura

### Backend
- **Domain Layer**: Entidades, Value Objects, Domain Services
- **Application Layer**: Use Cases, DTOs, Mappers
- **Adapters Layer**: REST Controllers, Repository Implementations
- **Infrastructure Layer**: Configuração, Segurança, Monitoramento

### Frontend
- **Features**: Módulos isolados por domínio (logs, metrics, alerts)
- **Components**: Componentes UI reutilizáveis
- **Services**: Clientes API e WebSocket
- **State Management**: Zustand (mínimo e modular)

## 🧪 Testes

### Testando o Backend

#### 1. Executar Testes Unitários
```bash
cd backend
./mvnw test
```

#### 2. Gerar Relatório de Cobertura
```bash
./mvnw test jacoco:report
# Relatório disponível em: backend/target/site/jacoco/index.html
```

#### 3. Testar API Manualmente

Após iniciar o backend, você pode testar os endpoints:

**Criar um log:**
```bash
curl -X POST http://localhost:8080/api/v1/logs \
  -H "Content-Type: application/json" \
  -d '{
    "level": "INFO",
    "message": "Test log entry",
    "service": "test-service",
    "host": "localhost"
  }'
```

**Listar logs:**
```bash
curl http://localhost:8080/api/v1/logs
```

**Listar logs com filtros:**
```bash
curl "http://localhost:8080/api/v1/logs?level=ERROR&service=test-service&page=0&size=10"
```

**Health Check:**
```bash
curl http://localhost:8080/api/v1/logs/health
```

#### 4. Acessar Documentação da API
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

### Testando o Frontend

#### 1. Executar Testes
```bash
cd frontend
npm test
```

#### 2. Testes com Cobertura
```bash
npm run test:coverage
```

#### 3. Verificar TypeScript
```bash
npm run type-check
```

#### 4. Testar Aplicação Manualmente

1. Inicie o frontend: `npm run dev`
2. Acesse http://localhost:3000
3. Navegue para `/logs` para ver a lista de logs
4. Navegue para `/dashboard` para ver o dashboard

### Testando a Integração Completa

#### 1. Iniciar Todos os Serviços
```bash
# Terminal 1: Serviços de infraestrutura
docker-compose up -d

# Terminal 2: Backend
cd backend
./mvnw spring-boot:run

# Terminal 3: Frontend
cd frontend
npm run dev
```

#### 2. Verificar Serviços
```bash
# Verificar PostgreSQL
docker exec -it observability-postgres psql -U observability -d observability_db -c "SELECT COUNT(*) FROM logs;"

# Verificar Redis
docker exec -it observability-redis redis-cli ping

# Verificar Kafka
docker exec -it observability-kafka kafka-topics --list --bootstrap-server localhost:9092
```

#### 3. Fluxo de Teste Completo

1. **Criar logs via API:**
```bash
for i in {1..5}; do
  curl -X POST http://localhost:8080/api/v1/logs \
    -H "Content-Type: application/json" \
    -d "{
      \"level\": \"INFO\",
      \"message\": \"Test log $i\",
      \"service\": \"test-service\",
      \"host\": \"localhost\"
    }"
  echo ""
done
```

2. **Verificar no Frontend:**
   - Acesse http://localhost:3000/logs
   - Os logs criados devem aparecer na lista

3. **Verificar no Banco de Dados:**
```bash
docker exec -it observability-postgres psql -U observability -d observability_db -c "SELECT id, level, message, service FROM logs ORDER BY timestamp DESC LIMIT 5;"
```

## 📝 Convenções

- **Commits**: Conventional Commits em inglês
- **Código**: Comentários e nomenclatura em inglês
- **Cobertura**: Mínimo de 80% de cobertura de testes

## 📚 Documentação

Consulte o [README detalhado](observability_readme.md) para mais informações sobre arquitetura, funcionalidades e diretrizes de desenvolvimento.

