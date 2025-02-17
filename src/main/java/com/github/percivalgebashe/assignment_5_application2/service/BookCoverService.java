package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import org.springframework.stereotype.Service;

@Service
public interface BookCoverService {

    void deleteBook(String id);

    BookCover saveBook(BookCoverDTO bookCoverDTO);

    BookCover getBookCover(String id);

    BookCover updateBookCover(BookCoverDTO bookCoverDTO);
}
