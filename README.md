# Ocean View Resort (OVR) Reservation System — Java EE + React

A simple reservation management web application for **Ocean View Resort** built with:
- **Java EE (Servlets)** backend (no Spring / no frameworks)
- **React** frontend (login + dashboard UI)
- **MySQL** database (`ocean_view_resort`)
- **Gson** for JSON serialization
- **JUnit 5** for automated backend tests

---

## Features

### Backend (Java EE / Servlets)
- ✅ Login (POST `/api/login`)
- ✅ Room Rates (GET `/api/room-rates`)
- ✅ Reservations CRUD:
  - Create (POST `/api/reservations`)
  - Read all (GET `/api/reservations`)
  - Read by ID (GET `/api/reservations?id=R001`)
  - Update (PUT `/api/reservations?id=R001`)
  - Delete (DELETE `/api/reservations?id=R001`)
- ✅ Price Calculator (GET `/api/price?roomType=DELUXE&checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD`)
- ✅ CORS enabled for `/api/*`
- ✅ Validation + custom exceptions

### Frontend (React)
- ✅ Login page (with reset)
- ✅ Dashboard layout (top header + left sidebar navigation)
- ✅ Room Rates page
- ✅ Reservations CRUD page (forms + reset + busy/loading handling)
- ✅ Price Calculator page
- ✅ Help / User Guide page for new staff

---

## Tech Stack

**Backend**
- Java 11
- Java EE 8 (`javax:javaee-api:8.0` provided by Payara)
- MySQL Connector/J
- Gson
- JUnit 5

**Frontend**
- React (Create React App)
- Fetch API

---

## Database

**Database name:** `ocean_view_resort`  
**Tables:** `users`, `room_rates`, `reservations`

> Ensure your backend DB connection settings (URL/username/password) match your MySQL setup.

---

## API Endpoints

Base URL (example):  
`http://localhost:8080/OVRReservation`

| Feature | Method | Endpoint |
|--------|--------|----------|
| Health check | GET | `/api/hello` |
| Login | POST | `/api/login` |
| Room rates | GET | `/api/room-rates` |
| Reservations (all) | GET | `/api/reservations` |
| Reservations (by id) | GET | `/api/reservations?id=R001` |
| Create reservation | POST | `/api/reservations` |
| Update reservation | PUT | `/api/reservations?id=R001` |
| Delete reservation | DELETE | `/api/reservations?id=R001` |
| Price calculation | GET | `/api/price?roomType=DELUXE&checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD` |

---

## Sample Requests

### Login (POST `/api/login`)
```json
{
  "username": "admin",
  "password": "1234"
}

# Ocean View Resort (OVR) Reservation System — Java EE + React

A simple reservation management web application for **Ocean View Resort** built with:
- **Java EE (Servlets)** backend (no Spring / no frameworks)
- **React** frontend (login + dashboard UI)
- **MySQL** database (`ocean_view_resort`)
- **Gson** for JSON serialization
- **JUnit 5** for automated backend tests

---

## Features

### Backend (Java EE / Servlets)
- ✅ Login (POST `/api/login`)
- ✅ Room Rates (GET `/api/room-rates`)
- ✅ Reservations CRUD:
  - Create (POST `/api/reservations`)
  - Read all (GET `/api/reservations`)
  - Read by ID (GET `/api/reservations?id=R001`)
  - Update (PUT `/api/reservations?id=R001`)
  - Delete (DELETE `/api/reservations?id=R001`)
- ✅ Price Calculator (GET `/api/price?roomType=DELUXE&checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD`)
- ✅ CORS enabled for `/api/*`
- ✅ Validation + custom exceptions

### Frontend (React)
- ✅ Login page (with reset)
- ✅ Dashboard layout (top header + left sidebar navigation)
- ✅ Room Rates page
- ✅ Reservations CRUD page (forms + reset + busy/loading handling)
- ✅ Price Calculator page
- ✅ Help / User Guide page for new staff

---

## Tech Stack

**Backend**
- Java 11
- Java EE 8 (`javax:javaee-api:8.0` provided by Payara)
- MySQL Connector/J
- Gson
- JUnit 5

**Frontend**
- React (Create React App)
- Fetch API

---

## Database

**Database name:** `ocean_view_resort`  
**Tables:** `users`, `room_rates`, `reservations`

> Ensure your backend DB connection settings (URL/username/password) match your MySQL setup.

---

## API Endpoints

Base URL (example):  
`http://localhost:8080/OVRReservation`

| Feature | Method | Endpoint |
|--------|--------|----------|
| Health check | GET | `/api/hello` |
| Login | POST | `/api/login` |
| Room rates | GET | `/api/room-rates` |
| Reservations (all) | GET | `/api/reservations` |
| Reservations (by id) | GET | `/api/reservations?id=R001` |
| Create reservation | POST | `/api/reservations` |
| Update reservation | PUT | `/api/reservations?id=R001` |
| Delete reservation | DELETE | `/api/reservations?id=R001` |
| Price calculation | GET | `/api/price?roomType=DELUXE&checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD` |

---

## Sample Requests

### Login (POST `/api/login`)
```json
{
  "username": "admin",
  "password": "1234"
}

Create Reservation (POST /api/reservations)
{
  "reservationNo": "R003",
  "guestName": "New Guest",
  "address": "Colombo",
  "contactNo": "0770000000",
  "roomType": "STANDARD",
  "checkIn": "2026-03-10",
  "checkOut": "2026-03-12"
}

Backend Setup (Java EE)
1) Requirements

Java 11

Payara Server (Java EE 8 compatible)

MySQL

Maven (recommended)

2) Create Database + Tables

Create the database and tables in MySQL:

Database: ocean_view_resort

Tables: users, room_rates, reservations

(Use the SQL script from your project/report.)

3) Configure DB Connection

Update your backend DB connection settings (example class: lk.icbt.oceanview.db.DB) to match your MySQL:

URL: jdbc:mysql://localhost:3306/ocean_view_resort

Username: <your_mysql_user>

Password: <your_mysql_password>

4) Build WAR

Using IntelliJ Maven tool window:

Lifecycle → clean

Lifecycle → package

This generates:

target/OVRReservation.war

5) Deploy to Payara

Deploy the WAR using Payara Admin Console or IntelliJ deployment.

Expected backend base URL:

http://localhost:8080/OVRReservation

Frontend Setup (React)
1) Requirements

Node.js + npm

2) Install dependencies
cd ovr-frontend
npm install
3) Configure API base (optional)

Create a .env file inside ovr-frontend:

REACT_APP_API_BASE=http://localhost:8080/OVRReservation
4) Run frontend
npm start

Frontend runs at:

http://localhost:3000

Running Automated Tests (Backend)

JUnit tests are in:

src/test/java/...

Run in IntelliJ:

Right click test class → Run

Or run via Maven:

mvn test
Project Structure (High Level)
Backend Packages

api — Servlets (controllers)

service — business logic + validation

dao — database access (JDBC)

model — domain models + enums

dto — request/response DTOs

pricing — Strategy pattern for pricing

factory — Factory pattern for wiring DAOs/services

exception — custom exceptions

filter — CORS filter

Frontend

src/components — layout + login

src/pages — Room Rates / Reservations / Price Calculator / Help pages

Notes (Assignment Compliance)

Built using Java EE Servlets (web service style) ✅

No Java frameworks like Spring Boot / Quarkus / Struts ✅

Uses only allowed dependencies:

Database driver ✅

Serialization (Gson) ✅

JUnit ✅
