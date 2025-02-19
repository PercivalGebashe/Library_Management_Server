package com.github.percivalgebashe.assignment_5_application2.service;

import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookCoverService {

    void deleteBook(String id);

    void deleteAllById(List<String> ids);

    BookCoverDTO saveBook(BookCoverDTO bookCoverDTO);

    Page<BookCoverDTO> findAll(Pageable pageable);

    BookCoverDTO getBookCover(String id);

    BookCoverDTO updateBookCover(BookCoverDTO bookCoverDTO);

    List<BookCoverDTO> updateAllBookCovers(List<BookCoverDTO> bookCoverDTOList);

}
