# Regulatory Policy Alignment Project

## Overview

The Regulatory Policy Alignment Project is a Spring Boot based backend application developed during the Java Full Stack Internship. The project is designed to securely manage organizational policy records using REST APIs.

The application supports JWT Authentication, CRUD operations, Redis caching, Docker integration, MailHog email testing, and Spring Boot Actuator health monitoring.

The system was tested using Postman and Docker Compose to ensure proper backend integration and service communication.

---

# Architecture Diagram

```text
Frontend / Postman
        ↓
Spring Boot REST API
        ↓
Spring Security + JWT
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
MySQL Database

Additional Services:
- Redis Cache
- MailHog Email Service
- Docker Compose
- Spring Boot Actuator
```

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Programming Language |
| Spring Boot 3 | Backend Framework |
| Spring Security | Authentication & Authorization |
| JWT | Token-based Security |
| MySQL | Database |
| Redis | Caching |
| Docker | Containerization |
| Docker Compose | Multi-container Setup |
| MailHog | Email Testing |
| Spring Boot Actuator | Health Monitoring |
| Maven | Build Tool |
| Postman | API Testing |
| VS Code | Development Environment |

---

# Features

## Authentication
- User Registration
- User Login
- JWT Token Generation
- Protected APIs using Authorization Header

## Policy Management
- Create Policy
- Get All Policies
- Get Policy By ID
- Update Policy
- Delete Policy

## Additional Features
- Global Exception Handling
- Redis Caching
- Docker Integration
- Email Notification Testing
- Health Monitoring
- Unit Testing

---

# Prerequisites

Before running the project, install the following software:

| Software | Version |
|---|---|
| Java | 17 |
| Maven | Latest |
| Docker Desktop | Latest |
| Git | Latest |
| VS Code | Latest |

---

# Setup Steps

## 1. Clone Repository

```bash
git clone <repository-url>
```

---

## 2. Open Project

Open the project folder in VS Code.

---

## 3. Start Docker Desktop

Make sure Docker Desktop is running successfully.

---

## 4. Build Project

```bash
.\mvnw.cmd clean install
```

---

## 5. Run Docker Containers

```bash
docker-compose up --build
```

---

## 6. Run Spring Boot Application

```bash
.\mvnw.cmd spring-boot:run
```

---

# Application URLs

| Service | URL |
|---|---|
| Backend API | http://localhost:8080 |
| Health Endpoint | http://localhost:8080/actuator/health |
| MailHog | http://localhost:8025 |
| phpMyAdmin | http://localhost:8081 |

---

# Environment Reference Table

| Property | Description |
|---|---|
| spring.datasource.url | MySQL Database URL |
| spring.datasource.username | Database Username |
| spring.datasource.password | Database Password |
| spring.jpa.hibernate.ddl-auto | Hibernate Table Strategy |
| jwt.secret | Secret Key for JWT |
| spring.data.redis.host | Redis Host |
| spring.data.redis.port | Redis Port |
| spring.mail.host | MailHog SMTP Host |
| spring.mail.port | MailHog SMTP Port |
| management.endpoints.web.exposure.include | Exposed Actuator Endpoints |

---

# API Endpoints

## Authentication APIs

### Register User

```http
POST /auth/register
```

### Login User

```http
POST /auth/login
```

### Refresh Token

```http
POST /auth/refresh
```

---

## Policy APIs

### Create Policy

```http
POST /api/policy-records
```

### Get All Policies

```http
GET /api/policy-records
```

### Get Policy By ID

```http
GET /api/policy-records/{id}
```

### Update Policy

```http
PUT /api/policy-records/{id}
```

### Delete Policy

```http
DELETE /api/policy-records/{id}
```

---

# Health Check API

```http
GET /actuator/health
```

Current Status:
- MySQL → UP
- Redis → UP
- MailHog → UP

---

# Docker Services

- Spring Boot Backend
- MySQL Database
- Redis Cache
- MailHog
- phpMyAdmin

---

# Testing

The following tests were performed:

- CRUD API Testing
- JWT Authentication Testing
- Validation Testing
- Error Handling Testing
- Docker Integration Testing
- Health Endpoint Testing
- Email API Testing
- Unit Testing using JUnit 5 & Mockito

---

# Conclusion

The Regulatory Policy Alignment Project successfully demonstrates secure backend API development using Spring Boot with JWT Authentication, Redis Caching, Docker Integration, MailHog Email Testing, and Spring Boot Actuator Health Monitoring.