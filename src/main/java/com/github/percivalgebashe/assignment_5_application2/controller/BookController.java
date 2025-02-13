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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        try {
            return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
        }catch (BadRequestException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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