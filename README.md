# Progress Service

A Spring Boot microservice that processes and analyzes course progress events for an e-learning platform.  
It validates and stores user course activity, then provides aggregated insights into course success rates.

---

## Features

| Endpoint | Description |
|-----------|--------------|
| **POST** `/v1/events` | Accepts and stores a new course progress event |
| **GET** `/v1/events/user/{userId}` | Returns all events for a given user (chronologically sorted) |
| **GET** `/v1/analysis/course/{courseId}` | Returns analysis for a course including pass/fail statistics and success rate |

---

## Architecture Overview

**Tech Stack:**
- **Java 17+**, **Spring Boot 3+**
- **H2** in-memory database
- **Spring Data JPA**
- **JUnit 5 + Mockito** for testing
- **Maven** for build and dependency management
- **JaCoCo** for code coverage
- **GitHub Actions** for CI/CD automation
- **Docker** for containerization

**Design:**
- Controller layer exposes REST endpoints
- Service layer handles business logic (analysis, aggregation)
- Repository layer persists data in H2
- Tests are layered (unit + integration) for maintainability

---

## Test Strategy

Testing was designed to ensure reliability and separation of concerns.

### **Unit Tests**
- Target: `CourseProgressService`
- Repository layer mocked using **Mockito**
- Focused on:
    - Correct participant counting
    - Accurate pass-rate calculation
    - Handling of division-by-zero (no users passed/failed)

### **Integration Tests**
- Full context using **@SpringBootTest**
- Uses **H2** in-memory DB
- Verifies:
    - REST endpoints’ responses and status codes
    - JSON structure and data correctness
    - Full data persistence flow (POST → GET → analysis)

### **Coverage**
- **JaCoCo** integrated via Maven plugin
- Run locally:
  ```bash
  mvn clean test
[INFO] --- jacoco:0.8.10:report (report) @ progress-service ---
[INFO] Skipping JaCoCo execution due to missing execution data file.


## **CI/CD Pipeline**

- Built using GitHub Actions
- Automatically triggers on pushes and pull requests to main branch

### **Steps:**

1. Checkout code
2. Set up JDK 17 and Maven
3. Build project and run all tests
4. Upload test and JaCoCo coverage reports
5. Docker build and push to GitHub Container Registry (ghcr.io) for main branch only


### Multi-stage Dockerfile for efficient builds:

- Build stage: compiles project with Maven
- Runtime stage: runs Spring Boot jar on Java 17 JRE

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

