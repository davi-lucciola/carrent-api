# 🚗 CarrentAPI

Carrent is a backend service that allows you to manage a fleet of vehicles and make them available for rent.
The user rent the vehicle and picks up and return with an operator by given a security code.

## ⚙️ Tech Stack

| Layer      | Tech                    |
|------------|-------------------------|
| Backend    | Java, Spring Boot, JPA  |
| Database   | SQL Server (via Docker) |
| DevOps  | Docker, Junit, Mockito  |

⸻

## 🚀 Features
- 🔒 JWT-based authentication
- 🚗 Vehicles Management
- 🏍️ Vehicle Types Management
- 🛣️ Rent Vehicles by Default Users and Management by Admin Users
- 🔁 Recurrent JOB to Cancel Expired Rent Solicitations
- 🐳 Dockerized development environment

[//]: # (- 🧪 Automated Unit Test )

⸻

## 📂 Project Structure

<pre>

```
carrent-api
├── src/                                         # Source Code Folder
│   ├── main/              
│   │   ├── java
│   │   │   └── io/api/carrent                   # Main Package Java Application
│   │   │       ├── api                          # Api Layer Package to implements REST endpoints and Swagger Docs
│   │   │       ├── app                          # App Layer Package to implement the domain services
│   │   │       ├── domain                       # Domain Layer to implements entities and define the domain services interface
│   │   │       ├── infra                        # Infra Layer to implements external services ports defined in app layer
│   │   │       └── CarrentApplication.java      # Spring Boot Entrypoint
│   │   └── resources
│   │        ├── db/migration                    # Migrations with Flyway
│   │        └── application.yml                 # Application Config File
│   └── tests/                                   # Automated tests with JUnit
│
├── docker-compose.yml			
└── README.md
```
</pre>

## 🏛️ Application Architecture

I use Domain Driven Design and Hexagonal Architecture principles to build the API and isolate the domain (I don't completed isolate because that will be overengineering in this case)
But all the business roles (Domain Services) are defined by "/domain/services" interfaces and implemented by my Application Services in "/app/services". 

In the Application Services any external service is defined by a interface and implemented by the Infra Layer

I also implemented Command Queries Responsibility Segregation (CQRS) to enable my API to separate what database will write and what will read scaling my databases horizontally (Besides now i am using only one database instance)


## 🪟 Demonstration

## 🚀 Getting Started

### 📦 Requirements
	• Java 17
	• Docker & Docker Compose

⸻

## 🐳 Start with Docker

### Build and run everything

`docker-compose up --build`

You can access the backend API docs at http://localhost:8080/swagger-ui/index.html.

⸻

[//]: # (## 🧪 Run Tests)

[//]: # ()
[//]: # (Backend tests &#40;pytest&#41;:)

[//]: # ()
[//]: # (- cd server)

[//]: # (- uv sync)

[//]: # (- uv run pytest)