package com.github.percivalgebashe.assignment_5_application2.controller;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ConflictException;
import com.github.percivalgebashe.assignment_5_application2.exception.NoContentFoundException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAuthorById(id));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllAuthors(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/authors", consumes = "application/json")
    public ResponseEntity<Object> getAuthorsByIds(@RequestBody List<String> authorIds){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthorsById(authorIds));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "admin/add", consumes = "application/json")
    public ResponseEntity<Object> addAuthor(@RequestBody AuthorDTO authorDTO){
        try {
            System.out.println("Adding author: " + authorDTO);
            return ResponseEntity.status(HttpStatus.OK).body(authorService.addAuthor(authorDTO));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ConflictException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping(value = "/admin/update", consumes = "application/json")
    public ResponseEntity<Object> updateAuthor(@RequestBody AuthorDTO author){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.updateAuthor(author));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(value = "/admin/update_auhhors")
    public ResponseEntity<Object> updateAuthors(List<AuthorDTO> authors){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.updateAuthors(authors));
        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") String id){
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("admin/delete")
    public ResponseEntity<Object> deleteAuthors(List<String> ids){
        try {
            authorService.deleteAuthors(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ids);

        }catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
