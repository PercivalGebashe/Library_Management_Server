package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Author addAuthor(AuthorDTO authorDTO);

    Author updateAuthor(AuthorDTO author);

    List<Author> updateAuthors(List<AuthorDTO> authors);

    void deleteAuthor(Long id);

    void deleteAuthors(List<Long> ids);

    List<Author> getAllAuthorsById(List<Long> authorIds);
}
