package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<Book> findAll(Pageable pageable);

    Book findById(Long id);

    Page<Book> findBookByFilter(BookFilterDTO filter, Pageable pageable);

    Book save(BookDTO book);

    List<Book> saveAll(List<BookDTO> books);

    Book updateBook(BookDTO bookDTO);

    List<Book> updateBooks(List<BookDTO> bookDTOs);
}
