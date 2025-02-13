package com.github.percivalgebashe.assignment_5_application2.controller;

import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.service.BookService;
import com.github.percivalgebashe.assignment_5_application2.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<Book> getBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping({"/id"})
    public ResponseEntity<Book> getBookById(@RequestParam Long id) {
        try {
            Optional<Book> book = bookService.findById(id);
            return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/filter")
    public Page<Book> getBooksFilter(@RequestBody BookFilterDTO filter, Pageable pageable) {
        return bookService.findBookByFilter(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        try {
            Optional<Book> bookEntity = bookService.save(book);
            return bookEntity.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    public ResponseEntity<Book> editBook(@RequestBody BookDTO bookDTO) {
        try {
            Optional<Book> updatedBook = bookService.updateBook(bookDTO);
            return new ResponseEntity<>(updatedBook.get(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // TODO: return response entity with error msg
        }catch (BadRequestException e){
            // TODO: return response entity with error msg
        }
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    public ResponseEntity<Book> editBook(@RequestBody BookDTO bookDTO) {
        try {
            Optional<Book> updatedBook = bookService.updateBook(bookDTO);
            return new ResponseEntity<>(updatedBook.get(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // TODO: return response entity with error msg
        }catch (BadRequestException e){
            // TODO: return response entity with error msg
        }
    }
}