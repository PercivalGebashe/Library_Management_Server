package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    AuthorDTO getAuthorById(String id);

    List<AuthorDTO> getAllAuthors();

    AuthorDTO addAuthor(AuthorDTO authorDTO);

    Author updateAuthor(AuthorDTO author);

    List<AuthorDTO> updateAuthors(List<AuthorDTO> authors);

    void deleteAuthor(String id);

    void deleteAuthors(List<String> ids);

    List<AuthorDTO> getAllAuthorsById(List<String> authorIds);
}
