# 🧠 NeuroCloud

An AI-enhanced microservices architecture built with Spring Boot, Spring Cloud, and OAuth2, designed for secure resume analysis and orchestration.


## 📑 Table of Contents

- [About the Project](#-about-the-project)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Features](#-features)
- [Microservices](#-microservices)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Security](#-security)
- [Future Scope](#-future-scope)
- [Screenshots & Diagrams](#-screenshots--diagrams)
- [License](#-license)

---

## 🧩 About the Project

NeuroCloud is a modular, secure, AI-integrated resume processing system built using modern Java and Spring technologies. It supports microservice orchestration, OAuth2 security, resume parsing, and intelligent matching for job applications.

Use Case: A user uploads their resume and a job description — NeuroCloud parses, evaluates, and rewrites the resume to match the job using AI.

---

## 🏗️ Architecture

# 🧠 NeuroCloud

An AI-enhanced microservices architecture built with Spring Boot, Spring Cloud, and OAuth2, designed for secure resume analysis and orchestration.

---

## 📑 Table of Contents

- [About the Project](#-about-the-project)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Features](#-features)
- [Microservices](#-microservices)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Security](#-security)
- [Future Scope](#-future-scope)
- [Screenshots & Diagrams](#-screenshots--diagrams)
- [License](#-license)

---

## 🧩 About the Project

NeuroCloud is a modular, secure, AI-integrated resume processing system built using modern Java and Spring technologies. It supports microservice orchestration, OAuth2 security, resume parsing, and intelligent matching for job applications.

Use Case: A user uploads their resume and a job description — NeuroCloud parses, evaluates, and rewrites the resume to match the job using AI.

---

## 🏗️ Architecture

[Client] --> [Gateway-Service] --> [Auth-Service] --> [User-Service]
|--> [Product-Service]
|--> [AI-Orchestration-Service] --> [Document-Analysis-Service]


Each microservice is independently deployable, discoverable via Eureka, and secured via OAuth2.

---

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot 3.x**
- **Spring Cloud** (Eureka, Gateway, Config)
- **OAuth2** with JWT (Spring Authorization Server)
- **H2** in-memory database (for development)
- **Apache Tika** for resume text extraction
- **Maven** (multi-module monorepo)
- **Postman** for API testing
- **Docker** (optional, future scope)

---

## ✨ Features

- 🧠 AI-powered resume matching and optimization
- 🔐 OAuth2-secured endpoints with JWT tokens
- 🔍 Service discovery and load balancing via Eureka
- ⚙️ Centralized config using Spring Cloud Config Server
- 📄 Resume parsing using Apache Tika
- ✅ Role-based access control (in progress)
- 🧪 Modular and testable via Postman
- 🚀 Ready for future Docker/Kubernetes deployment

---

## 🧩 Microservices Overview

| Service | Port | Description |
|--------|------|-------------|
| gateway-service | 8080 | API gateway for routing |
| auth-service | 8081 | OAuth2 Authorization Server |
| user-service | 8082 | User data management |
| product-service | 8083 | Demo product service |
| ai-orchestration-service | 8084 | Resume orchestration engine |
| document-analysis-service | 8085 | Resume parsing via Tika |
| config-server | 8071 | Centralized configuration |
| eureka-server | 8761 | Service registry & discovery |

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 21
- Maven
- Git
- Postman/Bruno

| Method | Endpoint                                       | Description                           |
| ------ | ---------------------------------------------- | ------------------------------------- |
| POST   | `/auth/token`                                  | Obtain JWT token (client credentials) |
| GET    | `/user-service/api/users`                      | Fetch all users                       |
| POST   | `/ai-orchestration-service/api/analyze-resume` | Analyze resume                        |
| POST   | `/document-analysis-service/api/extract-text`  | Extract text from resume              |

**🔐 Security**
NeuroCloud uses:

✅ OAuth2 Authorization Server for issuing JWTs

✅ Client Credentials and Password Grant types

✅ Role-based access (in progress)

✅ Gateway filter to validate all tokens centrally

✅ Encrypted inter-service calls via Bearer tokens

**🔮 Future Scope:**

🧾 Add persistent databases (PostgreSQL or MongoDB)

🧠 Embed LLMs (like GPT) for better resume enhancement

📦 Dockerize all services with Docker Compose

🕸 Deploy on Kubernetes with monitoring tools

📊 Add Kafka or RabbitMQ for async resume analysis pipeline

**🪪 License**
This project is licensed under the MIT License.


Crafted with ❤️ by Sreenidhi as part of an advanced Spring Boot microservices journey.

Demo:

ResumeExtractionAndOptimization.mp4
