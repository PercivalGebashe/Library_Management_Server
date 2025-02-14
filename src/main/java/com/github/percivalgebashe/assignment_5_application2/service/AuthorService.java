package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;

import java.util.List;

public interface AuthorService {

    Author getAuthorById(Long id);

    List<Author> getAllAuthors();

    Author addAuthor(Author author);

    Author updateAuthor(AuthorDTO author);

    List<Author> updateAuthors(List<AuthorDTO> authors);

    void deleteAuthor(Long id);

    void deleteAuthors(List<Long> ids);
}
