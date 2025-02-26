package com.github.percivalgebashe.assignment_5_application2.controller;

import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookFilterDTO;
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

@CrossOrigin(origins = "http://127.0.0.1:", methods = {
        RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.PUT,
        RequestMethod.DELETE,
}) // Allow requests from client
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getBooks(Pageable pageable) {

        Page<BookDTO> page = bookService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{searchType}/{searchQuery}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String searchType, @PathVariable String searchQuery) {
        try {
            if (searchType.equals("title")) {
                return new ResponseEntity<>(bookService.findByTitle(searchQuery), HttpStatus.OK);
            }
            if (searchType.equals("id")){
                return new ResponseEntity<>(bookService.findById(searchQuery), HttpStatus.OK);
            }
            if (searchType.equals("isbn")) {
                return new ResponseEntity<>(bookService.findByIsbn(searchQuery), HttpStatus.OK);
            }
            if (searchType.equals("genre")) {
                return new ResponseEntity<>(bookService.findByGenre(searchQuery), HttpStatus.OK);
            }
            throw new BadRequestException(String.format("Invalid searchType: %s", searchType));
        }catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<BookDTO>> getBooksFilter(@RequestBody BookFilterDTO filter, Pageable pageable) {
        try {
            Page<BookDTO> page = bookService.findBookByFilter(filter, pageable);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }catch (NoContentFoundException e){
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

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<BookDTO> editBook(@RequestBody BookDTO bookDTO) {
        try {
            System.out.println("Server bookDTO: " + bookDTO);
            return new ResponseEntity<>(bookService.updateBook(bookDTO), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBook(@RequestParam("bookId") String bookId) {
        try {
            System.out.println("delete bookDTO: " + bookId);
            bookService.deleteBook(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoContentFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}