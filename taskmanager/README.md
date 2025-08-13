# Task Manager API

A Spring Boot REST API for managing tasks. Built to match the assignment spec.

## Tech
- Spring Boot 2.7.x (Java 8)
- Spring Web, Spring Data JPA, Validation
- H2 in-memory database
- JUnit 5, MockMvc

## Run

```bash
mvn clean spring-boot:run
```

H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, user `sa`, no password).

## API

- `POST /api/tasks` – create
- `GET /api/tasks/<built-in function id>` – get by id
- `GET /api/tasks` – list (filters: `status`, `priority`, `dueBefore`, pagination `page,size`, sorting `sort=createdAt,desc`)
- `PUT /api/tasks/<built-in function id>` – update
- `DELETE /api/tasks/<built-in function id>` – delete

### Sample Request

```http
POST /api/tasks
Content-Type: application/json

{
  "title": "Complete Spring Boot Assignment",
  "description": "Build a task management API",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-08-14"
}
```

### Sample curl
```bash
curl -X POST http://localhost:8080/api/tasks   -H "Content-Type: application/json"   -d '{"title":"Complete Spring Boot Assignment","description":"Build a task management API","status":"PENDING","priority":"HIGH","dueDate":"2025-08-14"}'
```

## Tests
```bash
mvn -q -DskipTests=false test
```

All tests should pass.

## Notes
- Validation errors return 400 with field messages.
- Not found returns 404.
