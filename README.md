# 🐾 FurryHub – Pet Care Booking Platform

FurryHub is a **microservices-based platform** for pet owners to discover services and book appointments with providers (groomers, walkers, vets, etc.).  
It uses **Spring Boot, PostgreSQL, Kafka, and API Gateway** with JWT authentication.

---

## 🚀 Features

- 🔑 **Auth Service** – Register/login users, JWT-based authentication.
- 📋 **Catalog Service** – Manage providers, service types, provider–service mapping.
- 🐕 **Pet Service** – Manage pets for each user.
- 📅 **Booking Service** – Create/accept/cancel/complete bookings with validation.
- 🔔 **Notification Service** – Stores booking-related notifications for users/providers.
- 🌐 **API Gateway** – Secure, single entry point for all APIs.
- 🔍 **Eureka Server (optional)** – Service discovery.

---

## 🛠 Tech Stack

- **Java 21**, **Spring Boot 3.5**
- **Spring Cloud Gateway**
- **Spring Security (JWT)**
- **Spring Data JPA / Hibernate**
- **PostgreSQL** + **PostGIS** for provider locations
- **Apache Kafka** (event-driven messaging)
- **Lombok**, **Feign Client**, **MapStruct**

---

## 📦 Microservices Overview

| Service                  | Port | Description |
|--------------------------|------|-------------|
| **auth-service**         | 8081 | User authentication & JWT |
| **catalog-service**      | 8082 | Providers & service types |
| **pet-service**          | 8083 | Manage user pets |
| **booking-service**      | 8084 | Booking lifecycle |
| **notification-service** | 8085 | User/provider notifications |
| **api-gateway**          | 8080 | Gateway entry point |
| **eureka-server**        | 8761 | Service discovery (optional) |

---

## 🗄️ Database Setup

We use **PostgreSQL**. Each microservice has its own DB:

- `authdb`
- `catalogdb`
- `petdb`
- `bookingdb`
- `notificationdb`

### Enable PostGIS for CatalogDB
```sql
\c catalogdb;
CREATE EXTENSION IF NOT EXISTS postgis;
- **PostGIS** extension is enabled for storing provider locations as geographic points.
```

```⚙️ Setup Instructions
git clone https://github.com/your-username/furryhub.git
cd furryhub
```

2. Start PostgreSQL

```Make sure PostgreSQL is running locally. Create DBs:
CREATE DATABASE authdb;
CREATE DATABASE catalogdb;
CREATE DATABASE petdb;
CREATE DATABASE bookingdb;
CREATE DATABASE notificationdb;
```

3. Start Kafka (command for macbook)
   ```
   bin/kafka-server-start.sh config/kraft/server.properties

5. Run Services
```From project root:
mvn spring-boot:run -pl auth-service
mvn spring-boot:run -pl catalog-service
mvn spring-boot:run -pl pet-service
mvn spring-boot:run -pl booking-service
mvn spring-boot:run -pl notification-service
mvn spring-boot:run -pl api-gateway
```

## 🔑 API Endpoints

### Auth
- **POST** `/auth/register`
- **POST** `/auth/login`

### Catalog
- **POST** `/providers`
- **GET** `/providers`
- **POST** `/service-types`

### Pet
- **POST** `/pets`
- **GET** `/pets`

### Booking
- **POST** `/bookings`
- **PUT** `/bookings/{id}/accept`
- **PUT** `/bookings/{id}/complete`
- **PUT** `/bookings/{id}/cancel`
- **GET** `/bookings/my`

### Notifications
- **GET** `/notifications`
- **PUT** `/notifications/{id}/read`

---

## 🔔 Event-Driven Booking Flow

1. **BOOKING_CREATED** → when a user books.  
2. **BOOKING_ACCEPTED** → when the provider accepts.  
3. **BOOKING_COMPLETED** → when the provider marks complete.  
4. **BOOKING_CANCELLED** → when user/provider cancels.  

> Each event is published to **Kafka**, consumed by **Notification Service**, and stored for the respective user/provider.

---

## 🧪 Testing with Postman

1. Import `furryhub.postman_collection.json` into Postman.  
2. **Register & Login** → copy the JWT token from `/auth/login`.  
3. Use the JWT as **Bearer Token** in Postman (Authorization tab).  
4. Try the booking flow:  
   - User creates a booking  
   - Provider accepts  
   - Notification is generated and available at `/notifications`.  
