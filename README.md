# Project Inventory

A home inventory management application for tracking items across the fridge, freezer, and pantry, with a built-in shopping list.

## Components

### Backend (`project-inventory-api`)
A Spring Boot 4 REST API that manages inventory items across storage locations. It uses Spring Data JPA to persist data in PostgreSQL and exposes endpoints for creating, updating, and deleting items in the fridge, freezer, pantry, and shopping list.

### Frontend (`project-inventory-viewer`)
An Angular 21 single-page application for viewing and managing household inventory through a browser interface. It communicates with the backend API and is served in production via Nginx.

### Database (`postgres`)
A PostgreSQL instance that stores inventory data across four tables: `fridge`, `freezer`, `pantry`, and `shopping_list`. The schema and seed data are initialized automatically on startup by the backend.

### Database Admin (`pgadmin`)
A pgAdmin 4 instance for inspecting and querying the PostgreSQL database. It is available at [http://localhost:8080](http://localhost:8080) when running via Docker Compose.

## Setup

### Prerequisites
- Docker and Docker Compose

### Running with Docker Compose

1. Clone the repository:
   ```
   git clone <repository-url>
   cd project-inventory
   ```

2. Start all services:
   ```
   docker compose up --build
   ```

3. Access the application:
   - Frontend: [http://localhost:3000](http://localhost:3000)
   - Backend API: [http://localhost:8888](http://localhost:8888)
   - pgAdmin: [http://localhost:8080](http://localhost:8080)

### Stopping the application

```
docker compose down
```

To remove volumes (database data) as well:
```
docker compose down -v
```
