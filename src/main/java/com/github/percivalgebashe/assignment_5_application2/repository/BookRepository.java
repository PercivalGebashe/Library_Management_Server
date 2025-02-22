package com.github.percivalgebashe.assignment_5_application2.repository;

import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByBookId(String bookId);
    Optional<Book> findByGenres(String genres);
}