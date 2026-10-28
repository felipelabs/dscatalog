# DSCatalog API

A Java/Spring Boot portfolio project for a product catalogue.

## What it demonstrates

- Paginated REST endpoints and CRUD operations for products and categories
- DTOs, request validation, service and repository layers
- Spring Data JPA persistence and Spring Security configuration
- Tests covering repository, service and REST resource behavior

## Technology

- Java 17
- Spring Boot 3.2
- Spring Web, Spring Data JPA, Spring Security
- H2 for local evaluation
- Maven Wrapper

## Run locally

Requires JDK 17.

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

The application activates the `test` profile by default and uses an in-memory H2 database with sample data. This setup is for local evaluation.

## API overview

- `/products` — list products with pagination; retrieve, create, update and delete products
- `/categories` — list categories with pagination; retrieve, create, update and delete categories
- `/users` — user resources

For implementation details, see `backend/src/main/java/com/devsuperior/dscatalog`.
