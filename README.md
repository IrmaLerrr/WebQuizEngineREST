# Quiz Engine REST API

A secure REST API for creating, managing, and solving quizzes with user authentication and progress tracking. This project was completed as part of the [Hyperskill](https://hyperskill.org/projects/91) educational project.

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=spring)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0-green?logo=springsecurity)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?logo=hibernate)
![REST](https://img.shields.io/badge/API-RESTful-25D366)
![Auth](https://img.shields.io/badge/Auth-Basic%20Auth-orange)
![Architecture](https://img.shields.io/badge/Architecture-OOP-blue)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)

## Features

- **User Authentication**: Register and authenticate users with Spring Security
- **Quiz Management**: Create, retrieve, update, and delete quiz questions
- **Quiz Solving**: Submit answers and get immediate feedback
- **Progress Tracking**: Track user's completed quizzes with pagination
- **Security**: Password encryption, role-based access control, HTTP Basic authentication
- **Database**: PostgreSQL for persistent storage
- **Validation**: Input validation for email format and password strength

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- Gradle
- Hibernate Validator

## Prerequisites

- Java 17 or higher
- Gradle
- PostgreSQL database
- Docker (optional, for containerized database)

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd quiz-engine
```

### 2. Set up PostgreSQL database

#### Using Docker (recommended)
Create a `docker-compose.yml` file:

```yaml
services:
  postgres:
    image: postgres:latest
    container_name: postgres-latest-db
    environment:
      POSTGRES_DB: postgres
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 123
    ports:
      - "5432:5432"
```

Start the database:
```bash
docker-compose up
```

#### Manual PostgreSQL installation
- Create a database named `postgres`
- Update credentials in `application.properties` if needed

### 3. Configure application

Update `src/main/resources/application.properties` if necessary:

```properties
spring.application.name=WebQuizEngineREST
server.port=8889

spring.datasource.username=postgres
spring.datasource.password=123
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### 4. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8889`

## API Documentation

### Authentication Endpoints

#### Register a new user
```http
POST /api/register
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password123"
}
```
**Response**: `200 OK` or `400 Bad Request`

### Quiz Endpoints

#### Create a quiz
```http
POST /api/quizzes
Authorization: Basic auth
Content-Type: application/json

{
    "title": "Sample Quiz",
    "text": "What is 2+2?",
    "options": ["1", "2", "3", "4"],
    "answer": [3]
}
```
**Response**: `200 OK` with created quiz

#### Get a quiz by ID
```http
GET /api/quizzes/{id}
Authorization: Basic auth
```
**Response**: `200 OK` with quiz details (without answer)

#### Get all quizzes (paginated)
```http
GET /api/quizzes?page=0
Authorization: Basic auth
```
**Response**: `200 OK` with paginated list

#### Solve a quiz
```http
POST /api/quizzes/{id}/solve
Authorization: Basic auth
Content-Type: application/json

{
    "answer": [0, 1, 2]
}
```
**Response**:
```json
{
    "success": true,
    "feedback": "Congratulations, you're right!"
}
```

#### Delete a quiz 
```http
DELETE /api/quizzes/{id}
Authorization: Basic auth
```
**Response**: `204 No Content`, `403 Forbidden`, or `404 Not Found`

#### Get completed quizzes (paginated)
```http
GET /api/quizzes/completed?page=0
Authorization: Basic auth
```
**Response**: `200 OK` with paginated list of completed quizzes (for authorized user)

#### Health check
```http
GET /api/health
Authorization: Basic auth
```
**Response**: `200 OK`

## Security

- HTTP Basic authentication for all protected endpoints
- Passwords encrypted using BCrypt
- Users have "ROLE_USER" authority
- Quiz deletion restricted to quiz creators
- Public endpoints: `/api/register`

## 📁 Project Structure

```
src/main/java/engine/
├── controller/           # REST controllers
│   ├── AuthController.java
│   └── QuizController.java
├── dto/                  # Data Transfer Objects
│   ├── QuizAnswer.java
│   ├── QuizCreateDTO.java
│   ├── QuizResultDTO.java
│   └── QuizSolveDTO.java
├── model/                # Entity classes
│   ├── AppUser.java
│   ├── QuizQuestion.java
│   └── QuizResult.java
├── repository/           # JPA repositories
│   ├── AppUserRepository.java
│   ├── QuizQuestionRepository.java
│   └── QuizResultRepository.java
├── service/              # Business logic
│   ├── AppUserAdapter.java
│   ├── AppUserDetailsServiceImpl.java
│   ├── DtoMapper.java
│   ├── QuizService.java
│   └── UserDetailsService.java
├── SecurityConfig.java   # Security configuration
└── WebQuizEngine.java    # Main application class
```

## Testing

You can test the API using tools like:
- Postman
- cURL
- Insomnia

Example cURL commands:

```bash
# Register user
curl -X POST http://localhost:8889/api/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"secret123"}'

# Create quiz (authenticated)
curl -X POST http://localhost:8889/api/quizzes \
  -u test@example.com:secret123 \
  -H "Content-Type: application/json" \
  -d '{"title":"Math Quiz","text":"2+2=?","options":["3","4","5"],"answer":[1]}'
```

## Notes

- The database schema auto-updates on application startup
- Pagination starts from page 0
- Quiz answers are zero-based indices of correct options
- Email format validation: must contain '@' and domain part
