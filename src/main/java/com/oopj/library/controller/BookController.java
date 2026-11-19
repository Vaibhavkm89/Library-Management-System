package com.oopj.library.controller;

import com.oopj.library.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    // ArrayList keeps book records in memory while the application is running.
    private final List<Book> books = new ArrayList<>();

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (book.getId() == null || book.getTitle() == null || book.getAuthor() == null
                || book.getCategory() == null || book.getAvailableCopies() == null) {
            return ResponseEntity.badRequest().body("All book fields are required.");
        }
        if (book.getAvailableCopies() < 0)
            return ResponseEntity.badRequest().body("Available copies cannot be negative.");
        if (findBook(book.getId()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A book with this ID already exists.");

        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Integer id) {
        Book book = findBook(id);
        if (book == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with ID: " + id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book oldBook = findBook(id);
        if (oldBook == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with ID: " + id);
        if (updatedBook.getTitle() == null || updatedBook.getAuthor() == null
                || updatedBook.getCategory() == null || updatedBook.getAvailableCopies() == null)
            return ResponseEntity.badRequest().body("Title, author, category and availableCopies are required.");
        if (updatedBook.getAvailableCopies() < 0)
            return ResponseEntity.badRequest().body("Available copies cannot be negative.");

        oldBook.setTitle(updatedBook.getTitle());
        oldBook.setAuthor(updatedBook.getAuthor());
        oldBook.setCategory(updatedBook.getCategory());
        oldBook.setAvailableCopies(updatedBook.getAvailableCopies());
        return ResponseEntity.ok(oldBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        Book book = findBook(id);
        if (book == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with ID: " + id);
        books.remove(book);
        return ResponseEntity.ok("Book deleted successfully.");
    }

    private Book findBook(Integer id) {
        for (Book book : books) {
            if (book.getId().equals(id)) return book;
        }
        return null;
    }
}