package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ConflictException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.repository.BookCoverRepository;
import com.github.percivalgebashe.assignment_5_application2.service.BookCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCoverServiceImpl implements BookCoverService {

    private final BookCoverRepository bookCoverRepository;

    @Autowired
    public BookCoverServiceImpl(BookCoverRepository bookCoverRepository) {
        this.bookCoverRepository = bookCoverRepository;
    }

    public BookCover getBookCover(Long id) {
        if(null == id) {
            throw new BadRequestException("Book cover id cannot be null.");
        }
        return bookCoverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %d not found", id)));
    }

    public BookCover updateBookCover(BookCoverDTO bookCoverDTO) {
        validateBookCoverUpdate(bookCoverDTO);

        return bookCoverRepository.findById(bookCoverDTO.getId())
                .map(existingBookCover -> {
                    existingBookCover.setImagePath(bookCoverDTO.getImage_path());
                    return existingBookCover;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Book cover with ID %d Not found",
                        bookCoverDTO.getId())));
    }

    public BookCover saveBook(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        validateBookCoverAdd(bookCoverDTO);

        if(!bookCoverRepository.existsByImagePath(bookCoverDTO.getImage_path())){
            return bookCoverRepository.save(bookCoverDTO.toEntity());
        }else {
            throw new ConflictException(String.format(
                    "Book Cover With PATH %s Already Exists",
                    bookCoverDTO.getImage_path()));
        }
    }

    private void validateBookCoverUpdate(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        if (null == bookCoverDTO.getId()){
            throw new BadRequestException("id cannot be null.");
        }
        if (null == bookCoverDTO.getBook_id()){
            throw new BadRequestException("Book cover id cannot be null.");
        }
        if (null == bookCoverDTO.getImage_path()){
            throw new BadRequestException("Image path cannot be null.");
        }
    }

    private void validateBookCoverAdd(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        if (null == bookCoverDTO.getBook_id()){
            throw new BadRequestException("Book cover id cannot be null.");
        }
        if (null == bookCoverDTO.getImage_path()){
            throw new BadRequestException("Image path cannot be null.");
        }
    }

    public void deleteBook(Long id) {
        if(null == id) {
            throw new BadRequestException("Book id cannot be null.");
        }

        if(!bookCoverRepository.existsById(id)){
            throw new ResourceNotFoundException(String.format("Book with ID %d not found", id));
        }
        bookCoverRepository.deleteById(id);
    }
}