package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;

public interface BookCoverService {

    void deleteBook(Long id);

    BookCover saveBook(BookCoverDTO bookCoverDTO);

    BookCover getBookCover(Long id);

    BookCover updateBookCover(BookCoverDTO bookCoverDTO);
}
