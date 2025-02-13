package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import com.github.percivalgebashe.assignment_5_application2.repository.BookCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookCoverServiceImpl {

    private final BookCoverRepository bookCoverRepository;

    @Autowired
    public BookCoverServiceImpl(BookCoverRepository bookCoverRepository) {
        this.bookCoverRepository = bookCoverRepository;
    }

    public Optional<BookCover> getBookCover(Long id) {
        return bookCoverRepository.findById(id);
    }
}
