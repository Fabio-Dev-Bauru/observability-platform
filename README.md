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

### Backend
```bash
cd backend
./mvnw test
./mvnw test jacoco:report
```

### Frontend
```bash
cd frontend
npm test
npm run test:coverage
```

## 📝 Convenções

- **Commits**: Conventional Commits em inglês
- **Código**: Comentários e nomenclatura em inglês
- **Cobertura**: Mínimo de 80% de cobertura de testes

## 📚 Documentação

Consulte o [README detalhado](observability_readme.md) para mais informações sobre arquitetura, funcionalidades e diretrizes de desenvolvimento.

