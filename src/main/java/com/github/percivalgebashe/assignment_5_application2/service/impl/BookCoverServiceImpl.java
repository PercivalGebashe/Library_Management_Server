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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookCoverServiceImpl implements BookCoverService {

    private final BookCoverRepository bookCoverRepository;

    @Autowired
    public BookCoverServiceImpl(BookCoverRepository bookCoverRepository) {
        this.bookCoverRepository = bookCoverRepository;
    }

    @Override
    public BookCoverDTO getBookCover(String id) {
        if(null == id) {
            throw new BadRequestException("Book cover id cannot be null.");
        }
        return DTOMapper.toBookCoverDto(bookCoverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %s not found", id))
                )
        );
    }

    @Override
    public BookCoverDTO updateBookCover(BookCoverDTO bookCoverDTO) {
        validateBookCoverUpdate(bookCoverDTO);

        return bookCoverRepository.findById(bookCoverDTO.getBookId())
                .map(existingBookCover -> {
                    existingBookCover.setImagePath(bookCoverDTO.getImagePath());
                    return DTOMapper.toBookCoverDto(existingBookCover);
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Book cover with ID %s Not found",
                        bookCoverDTO.getBookId())));
    }

    @Override
    public BookCoverDTO saveBook(BookCoverDTO bookCoverDTO) {
        if(null == bookCoverDTO) {
            throw new BadRequestException("Book cover cannot be null.");
        }
        validateBookCoverAdd(bookCoverDTO);

        if(!bookCoverRepository.existsByImagePath(bookCoverDTO.getImagePath())){
            return DTOMapper.toBookCoverDto(
                    bookCoverRepository.save(
                            DTOMapper.toBookCoverEntity(bookCoverDTO)
                    )
            );
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

    @Override
    public void deleteBook(String id) {
        if(null == id) {
            throw new BadRequestException("Book id cannot be null.");
        }

        if(!bookCoverRepository.existsById(id)){
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", id));
        }
        bookCoverRepository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<String> ids) {
        ids.stream()
                .map(id -> {
                    if(!bookCoverRepository.existsById(id)){
                        throw new ResourceNotFoundException(String.format("Book with ID %s not found", id));
                    }
                    return id;
                })
                .map(id -> {
                    bookCoverRepository.deleteById(id);
                    return id;
                })
                .close();
    }

    @Override
    public Page<BookCoverDTO> findAll(Pageable pageable) {
        Page<BookCover> books = bookCoverRepository.findAll(pageable);
        return books.map(DTOMapper::toBookCoverDto);
    }

    @Override
    public List<BookCoverDTO> updateAllBookCovers(List<BookCoverDTO> bookCoverDTOList) {
        return bookCoverDTOList.stream()
                .map(bookCoverDTO -> {
                    validateBookCoverUpdate(bookCoverDTO);
                    if (bookCoverRepository.existsById(bookCoverDTO.getBookId())) {
                        return DTOMapper.toBookCoverDto(
                                bookCoverRepository.saveAndFlush(DTOMapper.toBookCoverEntity(bookCoverDTO)));
                    }else {
                        throw new BadRequestException(String.format("Book Cover with ID %s Not found", bookCoverDTO.getBookId()));
                    }
                }).toList();
    }
}