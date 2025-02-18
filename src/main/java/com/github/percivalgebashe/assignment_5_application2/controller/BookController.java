package com.github.percivalgebashe.assignment_5_application2.controller;

import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.NoContentFoundException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.service.BookService;
import com.github.percivalgebashe.assignment_5_application2.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:8081") // Allow requests from client
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(Pageable pageable) {
        try {
            Page<BookDTO> page = bookService.findAll(pageable);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }catch (NoContentFoundException e) {
            System.out.println("NoContentFoundException");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping({"/id"})
    public ResponseEntity<Book> getBookById(@RequestParam String id) {
        try {
            return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
        }catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<Book>> getBooksFilter(@RequestBody BookFilterDTO filter, Pageable pageable) {
        try {
            return new ResponseEntity<>(bookService.findBookByFilter(filter, pageable), HttpStatus.OK);
        }catch (NoContentFoundException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody BookDTO book) {
        try {
            System.out.println(book);
            return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addBooks(@RequestBody List<BookDTO> books) {
        try {
            return new ResponseEntity<>(bookService.saveAll(books), HttpStatus.CREATED);
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    public ResponseEntity<Book> editBook(@RequestBody BookDTO bookDTO) {
        try {
            return new ResponseEntity<>(bookService.updateBook(bookDTO), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}