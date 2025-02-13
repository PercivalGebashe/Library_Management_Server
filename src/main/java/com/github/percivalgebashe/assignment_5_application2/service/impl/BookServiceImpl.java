package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.NoContentFoundException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.repository.BookRepository;
import com.github.percivalgebashe.assignment_5_application2.service.BookService;
import com.github.percivalgebashe.assignment_5_application2.specification.BookSpecification;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> findAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        if (page.isEmpty()){
            throw new ResourceNotFoundException("Books not found");
        }
        return page;
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %d not found", id)));
    }

    public Page<Book> findBookByFilter(BookFilterDTO filter, Pageable pageable) {
        Specification<Book> bookSpecification = BookSpecification.filterBooks(filter);
        Page<Book> page = bookRepository.findAll(bookSpecification, pageable);
        if (page.getSize() == 0){
           throw new NoContentFoundException("No books found");

        }
        return page;
    }

    public Book save(BookDTO bookDTO) {
        validateBookDTO(bookDTO);

        return bookRepository.saveAndFlush(bookDTO.toBookEntity());
    }

    public List<Book> saveAll(List<BookDTO> books) {
        if (books == null || books.isEmpty()) {
            throw new BadRequestException("Book list cannot be empty.");
        }

        for (BookDTO bookDTO : books) {
            validateBookDTO(bookDTO);
        }

        List<Book> bookEntities = books.stream()
                .map(BookDTO::toBookEntity)
                .map(bookRepository::saveAndFlush)
                .toList();

        return bookRepository.saveAll(bookEntities);
    }

    public Book updateBook(BookDTO bookDTO) {

        validateBookDTO(bookDTO);

        return bookRepository.findById(bookDTO.getBook_id())
                .map(existingBook ->{
                    updateEntity(bookDTO, existingBook);
                    return bookRepository.saveAndFlush(existingBook);
                })
                .orElseThrow(() ->new ResourceNotFoundException(String.format("Book with ID %d not found",
                        bookDTO.getBook_id())));
    }

    public List<Book> updateBooks(List<BookDTO> bookDTOs) {
        if (bookDTOs == null || bookDTOs.isEmpty()) {
            throw new BadRequestException("Book list cannot be empty.");
        }

        for (BookDTO bookDTO : bookDTOs) {
            validateBookDTO(bookDTO);
        }

        List<Book> books = bookDTOs.stream()
                .map(bookDTO -> bookRepository.findById(bookDTO.getBook_id())
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %d not found",
                                bookDTO.getBook_id())))).toList();

        return bookRepository.saveAll(books);
    }

    private void validateBookDTO(BookDTO bookDTO) {
        if (bookDTO.getBook_id() == null) {
            throw new BadRequestException("Book id cannot be empty.");
        }
        if (StringUtils.isBlank(bookDTO.getTitle())) {
            throw new BadRequestException("Book title cannot be empty.");
        }
        if (StringUtils.isBlank(bookDTO.getIsbn())) {
            throw new BadRequestException("Book ISBN cannot be empty.");
        }
        if (bookDTO.getPublishedDate() == null) {
            throw new BadRequestException("Published date is required.");
        }
        if (bookDTO.getAuthors() == null || bookDTO.getAuthors().isEmpty()) {
            throw new BadRequestException("Authors cannot be empty.");
        }
        if (bookDTO.getGenres() == null || bookDTO.getGenres().isEmpty()) {
            throw new BadRequestException("Genres cannot be empty.");
        }
        if (StringUtils.isBlank(bookDTO.getDescription())) {
            throw new BadRequestException("Description cannot be empty.");
        }
    }

    private void updateEntity(BookDTO bookDTO, Book bookToUpdate) {
        bookToUpdate.setTitle(bookDTO.getTitle());
        bookToUpdate.setIsbn(bookDTO.getIsbn());
        bookToUpdate.setPublishedDate(bookDTO.getPublishedDate());
        bookToUpdate.setAuthors(bookDTO.getAuthors());
        bookToUpdate.setGenres(bookDTO.getGenres());
        bookToUpdate.setDescription(bookDTO.getDescription());
    }
}
