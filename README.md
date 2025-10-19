# In-Store Offers Viewer - Backend API

Spring Boot REST API for the In-Store Offers Viewer application.

## Technology Stack

- Java 17+
- Spring Boot 3.2.0
- Spring Data JPA
- Maven
- HSQLDB (Development)
- PostgreSQL (Production)

## Project Structure

```
src/main/java/com/retail/offersviewer/
├── controller/     # REST API endpoints
├── service/        # Business logic layer
├── repository/     # Data access layer
├── entity/         # JPA entities
├── exception/      # Custom exceptions and handlers
└── config/         # Application configuration
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

#### Development Mode (HSQLDB)

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` using HSQLDB in-memory database.

#### Production Mode (PostgreSQL)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Make sure PostgreSQL is running and configure the database credentials via environment variables:
- `DB_USERNAME`: PostgreSQL username
- `DB_PASSWORD`: PostgreSQL password

### Building the Application

```bash
mvn clean package
```

### Running Tests

```bash
mvn test
```

## Configuration

The application supports two profiles:

- **dev** (default): Uses HSQLDB file-based database
- **prod**: Uses PostgreSQL database

Configuration files:
- `application.properties` - Common configuration
- `application-dev.properties` - Development profile
- `application-prod.properties` - Production profile

## API Documentation

API endpoints will be available at:
- Stores: `/api/stores`
- Offers: `/api/stores/{storeId}/offers`
- Categories: `/api/categories`

(Full API documentation will be added as endpoints are implemented)
