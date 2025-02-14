package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import com.github.percivalgebashe.assignment_5_application2.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    @Override
    public Author getAuthorById(Long id) {
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {
        return List.of();
    }

    @Override
    public Author addAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(AuthorDTO author) {
        return null;
    }

    @Override
    public List<Author> updateAuthors(List<AuthorDTO> authors) {
        return List.of();
    }

    @Override
    public void deleteAuthor(Long id) {

    }

    @Override
    public void deleteAuthors(List<Long> ids) {

    }
}
