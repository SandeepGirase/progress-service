# progress-service

## Project Overview

- progress-service is a Spring Boot microservice that processes course progress events for an e-learning platform.
- It validates and stores events, provides chronological user event queries, and calculates aggregated course analysis 
  metrics such as pass rates.

## Key Features

- Accepts course progress events (COURSE_STARTED, COURSE_PASSED, COURSE_FAILED)
- Returns chronological events for a specific user

### Aggregates course metrics:

- Participants started, passed, failed
- Pass rate with division-by-zero handling

## In-memory database (H2) for persistence
### Steps to check Dababase in local -
- Run spring boot application locally through Bash Terminal - $ mvn spring-boot:run
- Execute some API requests (insert data): $ curl -X POST http://localhost:8080/v1/events \
  -H "Content-Type: application/json" \
  -d '{
  "userId": "user1",
  "courseId": "course101",
  "timestamp": "2025-10-23T10:00:00Z",
  "eventType": "COURSE_STARTED"
  }'

- DB browse: "http://localhost:8080/h2-console"
- Jdbc url: jdbc:h2:mem:progressdb
- username: sandeep
- password: sandeep123
- 
## Automated testing with unit and integration tests

## CI/CD pipeline with GitHub Actions

## Dockerized for container deployment

## Microservice Endpoints
### Method	   Endpoint	                         Description
-   POST	   /v1/events	                     Submit a new course progress event
-   GET	       /v1/events/user/{userId}	         Retrieve chronological events for a user
-   GET	       /v1/analysis/course/{courseId}	 Retrieve aggregated analysis for a course


## Testing Strategy
### Unit Tests

- Focus on service layer logic, especially analysis metrics and pass rate calculations
- Mocked repository layer using Mockito
- Covered edge cases, including division-by-zero scenarios

### Integration Tests

- Focus on controller layer and API endpoints
- Used @SpringBootTest to test against H2 in-memory database
- Validated HTTP responses, JSON structure, and correctness of data processing

### Test Coverage

- Integrated JaCoCo Maven plugin to generate code coverage reports
- Reports available under target/site/jacoco/index.html

## CI/CD Pipeline

- Built using GitHub Actions
- Automatically triggers on pushes and pull requests to main branch

### Steps:

1. Checkout code
2. Set up JDK 17 and Maven
3. Build project and run all tests
4. Upload test and JaCoCo coverage reports
5. Docker build and push to GitHub Container Registry (ghcr.io) for main branch only

### Workflow file: .github/workflows/ci.yml

## Docker

### Multi-stage Dockerfile for efficient builds:

- Build stage: compiles project with Maven
- Runtime stage: runs Spring Boot jar on Java 17 JRE

### Exposes port 8080

## Build and run locally: In Bash Terminal 
$ docker build -t progress-service:local .
$ docker run -p 8080:8080 progress-service:local

## Use of AI Tools (Windsurf AI)

### During the development of this progress-service microservice,AI coding assistants were used to improve efficiency while maintaining control over critical logic.

- Where AI tools were used
- Boilerplate code generation: Controllers, services, and DTOs
- Test code scaffolding: Unit and integration tests with Mockito and @SpringBootTest
- CI/CD workflow: Generated GitHub Actions ci.yml template
- Debugging assistance: JaCoCo execution issues, Docker tag errors

### Example of a helpful AI prompt

> “Generate a GitHub Actions workflow for a Spring Boot Maven project that builds, runs tests, collects JaCoCo coverage, and optionally builds and pushes a Docker image to GitHub Container Registry.”

### Reflection

- Efficiency gains: Rapid generation of boilerplate and CI/CD workflow, brainstorming test structures
- Manual corrections needed: Implementing core service logic, validating workflow syntax, and ensuring Docker image tags are lowercase
- Conclusion: AI assistants sped up development but human oversight ensured correctness and adherence to best practices

## License
This project is for coding challenge submission purposes and does not have an external license.


