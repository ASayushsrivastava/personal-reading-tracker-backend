# 📚 Personal Reading Tracker

A cloud-ready full-stack web application that helps a user manage their personal reading journey. Rather than acting as a public library system, the application serves as a private digital reading journal where books can be organized, tracked, reviewed, and grouped into custom collections.

The project demonstrates modern full-stack development using **Spring Boot**, **PostgreSQL**, and a layered REST API architecture. The frontend will be developed using Angular.

---

# Features

## Book Management

* Add new books
* Update book information
* Delete books
* View all books
* Track reading progress
* Store personal ratings
* Store reading notes
* Automatically calculate reading progress

---

## Reading Status

Each book can have one of the following statuses:

* To Read
* Reading
* Completed
* Abandoned

Reading workflow:

```text
To Read
    │
    ▼
Reading
    │
 ┌──┴─────────┐
 ▼            ▼
Completed   Abandoned
```

---

## Collections

Organize books into unlimited custom collections.

Examples:

* Favorites
* Programming
* Productivity
* Fiction
* Philosophy
* 2026 Reading List
* Books to Re-read

A single book can belong to multiple collections.

---

## Dashboard

Provides quick reading statistics including:

* Total Books
* Currently Reading
* To Read
* Completed
* Abandoned
* Continue Reading
* Recently Added

---

# Technology Stack

## Backend

* Java 21
* Spring Boot 3.3.4
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven

## Database

* Neon PostgreSQL

## Frontend (Planned)

* Angular

## Deployment

* Backend: Azure App Service or Render
* Frontend: Cloudflare Pages

---

# Project Structure

```text
src
└── main
    ├── java
    │   └── com.bookshelve.backend
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── exception
    │       ├── repository
    │       ├── service
    │       │     └── impl
    │       └── BackendApplication.java
    │
    └── resources
          └── application.properties
```

---

# Architecture

```text
Angular Frontend
        │
        ▼
REST API
        │
        ▼
Spring Boot Backend
        │
Spring Data JPA
        │
        ▼
Neon PostgreSQL
```

---

# REST API

## Books

| Method | Endpoint          | Description      |
| ------ | ----------------- | ---------------- |
| GET    | `/api/books`      | Get all books    |
| GET    | `/api/books/{id}` | Get a book by ID |
| POST   | `/api/books`      | Add a new book   |
| PUT    | `/api/books/{id}` | Update a book    |
| DELETE | `/api/books/{id}` | Delete a book    |

---

## Collections

| Method | Endpoint                               | Description                     |
| ------ | -------------------------------------- | ------------------------------- |
| GET    | `/api/collections`                     | Get all collections             |
| POST   | `/api/collections`                     | Create a collection             |
| PUT    | `/api/collections/{id}`                | Rename a collection             |
| DELETE | `/api/collections/{id}`                | Delete a collection             |
| POST   | `/api/collections/{id}/books`          | Add a book to a collection      |
| DELETE | `/api/collections/{id}/books/{bookId}` | Remove a book from a collection |

---

## Dashboard

| Method | Endpoint                          | Description          |
| ------ | --------------------------------- | -------------------- |
| GET    | `/api/dashboard`                  | Dashboard statistics |
| GET    | `/api/dashboard/continue-reading` | Continue reading     |
| GET    | `/api/dashboard/recent`           | Recently added books |

---

# Running the Project Locally

## Prerequisites

* Java 21
* Maven
* PostgreSQL database (Neon recommended)
* Eclipse / IntelliJ IDEA

---

## Clone the repository

```bash
git clone https://github.com/ASayushsrivastava/personal-reading-tracker-backend.git
```

```bash
cd personal-reading-tracker-backend
```

---

## Configure Environment Variables

The application expects the following environment variables.

| Variable                   | Description         |
| -------------------------- | ------------------- |
| SPRING_DATASOURCE_URL      | PostgreSQL JDBC URL |
| SPRING_DATASOURCE_USERNAME | Database username   |
| SPRING_DATASOURCE_PASSWORD | Database password   |

Example:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>/<database>?sslmode=require
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
```

---

## application.properties

The project intentionally does **not** store database credentials.

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

server.port=${PORT:8080}
```

---

## Run the application

Using Maven:

```bash
mvn spring-boot:run
```

or directly from Eclipse by running:

```
BackendApplication.java
```

---

# Database

The application has been tested using:

* Neon PostgreSQL

Hibernate automatically updates the schema during development using:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

# Planned Features

* Search and filtering
* Automatic book cover retrieval
* Reading statistics
* Reading goals
* Export library
* Goodreads import/export
* Responsive Angular frontend
* Docker support
* CI/CD pipeline
* Monitoring and logging
* Custom domain
* Progressive Web App (PWA)

---

# Future Enhancements

* JWT Authentication
* Redis caching
* File uploads
* AI-generated reading summaries
* Reading streak tracking
* Book recommendations
* Dark mode
* Reading calendar

---

# License

This project is licensed under the MIT License.

---

# Author

**Ayush Srivastava**

GitHub: https://github.com/ASayushsrivastava
