# Project 2 - Library Management REST API (Spring Boot)

## Requirements
- JDK 17+
- IntelliJ IDEA
- Postman

## Run in IntelliJ
1. Open the `LibraryManagementSpringBoot` folder in IntelliJ.
2. Load `pom.xml` as a Maven project and wait for dependencies to download.
3. Open `LibraryManagementApplication.java` and run `main()`.
4. Wait for the console message that Tomcat started on port 8080.

This project intentionally uses an `ArrayList<Book>` collection; no MySQL database is required. Data is stored in memory and resets when the app stops.

## Postman tests
Base URL: `http://localhost:8080/books`

### Add a book
POST `http://localhost:8080/books`
Body > raw > JSON:
```json
{
  "id": 1,
  "title": "Java: The Complete Reference",
  "author": "Herbert Schildt",
  "category": "Programming",
  "availableCopies": 5
}
```
Expected: `201 Created`.

### Get all books
GET `http://localhost:8080/books`
Expected: `200 OK` and a JSON array.

### Get by ID
GET `http://localhost:8080/books/1`
Expected: `200 OK`. Try `/books/99` for `404 Not Found`.

### Update
PUT `http://localhost:8080/books/1`
Body > raw > JSON:
```json
{
  "title": "Core Java",
  "author": "Cay Horstmann",
  "category": "Programming",
  "availableCopies": 8
}
```
Expected: `200 OK` and updated details. The ID comes from the URL.

### Delete
DELETE `http://localhost:8080/books/1`
Expected: `200 OK` and `Book deleted successfully.` Try again for `404 Not Found`.

### Extra validation tests
- POST the same ID twice: second request returns `409 Conflict`.
- Send negative `availableCopies`: returns `400 Bad Request`.
- Send an incomplete book: returns `400 Bad Request`.

## Endpoint summary
| Method | URL | Operation |
|---|---|---|
| POST | `/books` | Add book |
| GET | `/books` | Retrieve all books |
| GET | `/books/{id}` | Retrieve one book |
| PUT | `/books/{id}` | Update book |
| DELETE | `/books/{id}` | Delete book |

## Viva quick notes
- `@SpringBootApplication`: application entry point and auto-configuration.
- `@RestController`: handles REST requests and returns response data.
- `@RequestMapping`: common URL prefix.
- `@PostMapping`, `@GetMapping`, `@PutMapping`, `@DeleteMapping`: map HTTP methods.
- `@RequestBody`: converts JSON request body to a Java object.
- `@PathVariable`: reads ID from the URL.
- `ArrayList`: stores book objects in memory.
- `ResponseEntity`: returns body with an HTTP status code.
