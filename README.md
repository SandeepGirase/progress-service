# progress

## Overview
Microservice that ingests course progress events and provides per-course analysis.

## Run locally
mvn spring-boot:run
H2 console: http://localhost:8080/h2-console

## Endpoints
POST /v1/events
GET /v1/events/user/{userId}
GET /v1/analysis/course/{courseId}

## Tests
Run all tests:
mvn clean verify
JaCoCo report: target/site/jacoco/index.html

## Test Strategy
### Unit Tests
- Location: `src/test/java/.../service`
- Purpose: isolate and verify business logic (EventService.analyzeCourse).
- Tools: JUnit 5, Mockito to mock repository methods.
- Edge cases: division by zero (no passed/failed), duplicate events from same user, large lists.

### Integration Tests
- Location: `src/test/java/.../controller`
- Purpose: test REST endpoints with SpringBootTest and in-memory H2 DB.
- Validate HTTP status codes, JSON payload shapes, and cross-layer wiring.

### Extending the Strategy
- End-to-end tests using a deployed environment + test containers for DB.
- Contract tests (Pact) if multiple services interact.
- Load tests (Gatling) for performance.
- Security tests for input validation and authentication.

## Use of AI Tools
I used an AI coding assistant to:
- Generate boilerplate code and suggest unit test structure.
- Draft the GitHub Actions workflow.

Example prompt used:
> "Generate a GitHub Actions workflow for a Maven Spring Boot project that runs `mvn clean verify` on push to main and builds + pushes a Docker image to ghcr.io."

Reflection:
- AI improved speed for routine files (CI, Dockerfile, DTOs).
- I reviewed and manually fixed type/validation nuances and test assertions — AI suggestions required domain knowledge and checking.

