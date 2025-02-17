package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ConflictException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.mapper.DTOMapper;
import com.github.percivalgebashe.assignment_5_application2.repository.BookCoverRepository;
import com.github.percivalgebashe.assignment_5_application2.service.BookCoverService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookCoverServiceImpl implements BookCoverService {

    private final BookCoverRepository bookCoverRepository;

    @Autowired
    public BookCoverServiceImpl(BookCoverRepository bookCoverRepository) {
        this.bookCoverRepository = bookCoverRepository;
    }

    public BookCover getBookCover(String id) {
        if(null == id) {
            throw new BadRequestException("Book cover id cannot be null.");
        }
        return bookCoverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %s not found", id)));
    }

    public BookCover updateBookCover(BookCoverDTO bookCoverDTO) {
        validateBookCoverUpdate(bookCoverDTO);

        return bookCoverRepository.findById(bookCoverDTO.getBookId())
                .map(existingBookCover -> {
                    existingBookCover.setImagePath(bookCoverDTO.getImagePath());
                    return existingBookCover;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Book cover with ID %s Not found",
                        bookCoverDTO.getBookId())));
    }

    public BookCover saveBook(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        validateBookCoverAdd(bookCoverDTO);

        if(!bookCoverRepository.existsByImagePath(bookCoverDTO.getImagePath())){
            return bookCoverRepository.save(DTOMapper.toBookCoverEntity(bookCoverDTO));
        }else {
            throw new ConflictException(String.format(
                    "Book Cover With PATH %s Already Exists",
                    bookCoverDTO.getImagePath()));
        }
    }

    private void validateBookCoverUpdate(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        if (null == bookCoverDTO.getBookId()){
            throw new BadRequestException("Book cover id cannot be null.");
        }
        if (null == bookCoverDTO.getImagePath()){
            throw new BadRequestException("Image path cannot be null.");
        }
    }

    private void validateBookCoverAdd(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        if (null == bookCoverDTO.getBookId()){
            throw new BadRequestException("Book cover id cannot be null.");
        }
        if (null == bookCoverDTO.getImagePath()){
            throw new BadRequestException("Image path cannot be null.");
        }
    }

    public void deleteBook(String id) {
        if(null == id) {
            throw new BadRequestException("Book id cannot be null.");
        }

        if(!bookCoverRepository.existsById(id)){
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", id));
        }
        bookCoverRepository.deleteById(id);
    }
}