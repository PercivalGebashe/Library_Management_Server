# Author and Book Management Server

## 📖 Overview
This is a Spring Boot-based RESTful API for managing authors, books, and genres. The application provides endpoints to create, retrieve, update, and delete books and authors while ensuring proper validation and exception handling.

## 🚀 Features
- Manage **Books**, **Authors**, and **Genres**
- Search and filter books using **criteria-based queries**
- **Pagination** support for large datasets
- **Exception handling** with `@RestControllerAdvice`
- **Validation** of incoming data to maintain consistency

## 🛠️ Technologies Used
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA** (Hibernate)
- **MySQL** (or any compatible database)
- **Lombok** (for reducing boilerplate code)
- **Maven** (for dependency management)

## 📂 Project Structure
```
📦 author_and_book_management_server
 ┣ 📂 src/main/java/com/github/percivalgebashe/assignment_5_application2
 ┃ ┣ 📂 controller   # REST API Controllers
 ┃ ┣ 📂 service      # Business logic and service layer
 ┃ ┣ 📂 entity       # JPA Entities (Book, Author, Genre)
 ┃ ┣ 📂 repository   # Database repositories
 ┃ ┣ 📂 dto          # Data Transfer Objects (DTOs)
 ┃ ┗ 📂 exception    # Custom exception handlers
 ┃ ┗ 📂 specification # Customer specification builder
 ┣ 📜 pom.xml        # Maven configuration
 ┗ 📜 application.yml # Database and application config
```

## 🔧 Setup and Installation
### 1️⃣ Clone the Repository
```bash
git clone https://github.com/PercivalGebashe/author_and_book_management_server.git
cd author_and_book_management_server
```
### 2️⃣ Configure the Database
Edit `application.properties` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/<your_database>?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```
### 3️⃣ Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

## 📌 API Endpoints
### 📚 Books
| Method | Endpoint           | Description |
|--------|-------------------|-------------|
| GET    | `/api/v1/books`      | Get all books |
| POST   | `/api/v1/books`      | Add a new book |
| GET    | `/api/v1/books/{id}` | Get book by ID |
| PUT    | `/api/v1/books/{id}` | Update a book |
| DELETE | `/api/v1/books/{id}` | Delete a book |

### ✍️ Authors
| Method | Endpoint          | Description |
|--------|------------------|-------------|
| GET    | `/api/v1/authors`   | Get all authors |
| POST   | `/api/v1/authors`   | Add a new author |
| GET    | `/api/v1/authors/{id}` | Get author by ID |
| PUT    | `/api/v1/authors/{id}` | Update an author |
| DELETE | `/api/v1/authors/{id}` | Delete an author |

### ✍️ BookCovers
| Method | Endpoint          | Description |
|--------|------------------|-------------|
| GET    | `/api/v1/book_cover/{id}` | Get Book cover image path by id(there can be multiple book covers for the same book_id) |
| POST   | `/api/v1/authors`   | Add a new author |
| PUT    | `/api/v1/book_cover/{id}` | Update a book cover |
| DELETE | `/api/v1/book_cover/{id}` | Delete a book cover |

## 🛡️ Exception Handling
- `ResourceNotFoundException` → 404 NOT FOUND
- `BadRequestException` → 400 BAD REQUEST
- `NoContentException` → 204 NO CONTENT
- Custom excepttion error message via `ErrorMessage` class
- Global exception handling via `ApiExceptionHandler` class

## 💡 Future Enhancements
- Implement **JWT authentication** for security
- Add **unit tests** for service and repository layers
- Expose **GraphQL API** alongside REST endpoints

## 📜 License
This project is open-source and available under the [MIT License](LICENSE).

---
💡 **Contributions are welcome!** Feel free to fork and modify as needed! 🚀

