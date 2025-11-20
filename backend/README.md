# Observability Center - Backend

Backend service built with Spring Boot and Hexagonal Architecture.

## Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- Redis 7+
- Kafka 3.0+ (optional)

## Running the Application

```bash
# Build the project
./mvnw clean install

# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run
```

## Architecture

The backend follows Hexagonal Architecture principles:

- **Domain Layer**: Core business logic, entities, value objects, and domain services
- **Application Layer**: Use cases, application services, and DTOs
- **Adapters Layer**: REST controllers, WebSocket handlers, repository implementations
- **Infrastructure Layer**: Configuration, security, and monitoring

## API Documentation

Once the application is running, access the API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

