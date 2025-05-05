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
- 🧪 Automated Unit Test 
- 🐳 Dockerized development environment

⸻

## 📂 Project Structure

<pre>

```
carrent-api
├── client/                # NextJS App
│   ├── src/           
│   │   ├── app/           # App Routing
│   │   ├── components/    # Global Components
│   │   ├── lib/           # Lib Modules
│   │   └── modules/       # App modules separated by domain
│   └── .env.exemple       # Env Variables Exemple
│
├── server/                # Flask APP
│   ├── knockbankapi/      # API Module
│   ├── migrations/        # Database Migrations
│   ├── tests/             # Automated tests with Pytest
│   └── .env.exemple       # Env Variables Exemple
│
├── docker-compose.yml			
└── README.md
```
</pre>

⸻

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

## 🧪 Run Tests

Backend tests (pytest):

- cd server
- uv sync
- uv run pytest