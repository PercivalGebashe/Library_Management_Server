package com.github.percivalgebashe.assignment_5_application2.controller;

import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ConflictException;
import com.github.percivalgebashe.assignment_5_application2.exception.NoContentFoundException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.service.impl.BookCoverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images/book_cover")
public class BookCoverController {

    @Autowired
    private BookCoverServiceImpl bookCoverService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookCover(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(bookCoverService.getBookCover(id), HttpStatus.OK);
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBookCover(@RequestBody BookCoverDTO bookCoverDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookCoverService.updateBookCover(bookCoverDTO));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createBookCover(@RequestBody BookCoverDTO bookCover) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookCoverService.saveBook(bookCover));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ConflictException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookCover(@PathVariable("id") Long id) {
        try {
            bookCoverService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted Book Cover");
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (NoContentFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}