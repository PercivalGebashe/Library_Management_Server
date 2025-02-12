package com.github.percivalgebashe.assignment_5_application2.repository;

import com.github.percivalgebashe.assignment_5.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBookRepository {

    Page<Book> findBookByFilters(BookFilterDTO filter, Pageable pageable);
}
