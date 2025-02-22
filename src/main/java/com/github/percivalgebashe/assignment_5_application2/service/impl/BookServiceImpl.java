package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ConflictException;
import com.github.percivalgebashe.assignment_5_application2.exception.NoContentFoundException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.mapper.DTOMapper;
import com.github.percivalgebashe.assignment_5_application2.repository.BookRepository;
import com.github.percivalgebashe.assignment_5_application2.service.BookService;
import com.github.percivalgebashe.assignment_5_application2.specification.BookSpecificationBuilder;
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

    public Page<BookDTO> findAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        if (page.isEmpty()){
            throw new NoContentFoundException("No Books Found");
        }
        return page.map(DTOMapper::toBookDto);

    }

    public BookDTO  findById(String id) {
        return bookRepository.findById(id).map(DTOMapper::toBookDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %s not found", id)));
    }

    public Page<BookDTO> findBookByFilter(BookFilterDTO filter, Pageable pageable) {
        Specification<Book> bookSpecification = BookSpecificationBuilder.getBookFilterCriteria(filter);
        Page<Book> page = bookRepository.findAll(bookSpecification, pageable);
        if (page.getSize() == 0){
           throw new NoContentFoundException("No books found");

        }
        return page.map(DTOMapper::toBookDto);
    }

    public Book save(BookDTO bookDTO) {
        validateBookDTOAdd(bookDTO);
        bookDTO.generateBookId();
        if(!bookRepository.existsById(bookDTO.getId())) {

            return bookRepository.saveAndFlush(DTOMapper.toBookEntity(bookDTO));
        }
        throw new ConflictException(String.format("Book with ID %s already exists", bookDTO.getId()));
    }

    public List<BookDTO> saveAll(List<BookDTO> books) {
        if (books == null || books.isEmpty()) {
            throw new BadRequestException("Book list cannot be empty.");
        }

        for (BookDTO bookDTO : books) {
            validateBookDTOAdd(bookDTO);
            bookDTO.generateBookId();
        }

        List<Book> bookEntities = books.stream()
                .map(DTOMapper::toBookEntity)
                .peek(book -> {
                    if (bookRepository.existsById(book.getBookId())){
                        throw new ConflictException("Book with ID " + book.getBookId() + " already exists");
                    }
                })
                .toList();
        System.out.println("Books to be saved: " + bookEntities);

        return bookRepository.saveAllAndFlush(bookEntities).stream().map(DTOMapper::toBookDto).toList();
    }

    public BookDTO updateBook(BookDTO bookDTO) {

        validateBookDTO(bookDTO);

        return bookRepository.findById(bookDTO.getId())
                .map(existingBook ->{
                    updateEntity(bookDTO, existingBook);
                    return DTOMapper.toBookDto(bookRepository.saveAndFlush(existingBook));
                })
                .orElseThrow(() ->new ResourceNotFoundException(String.format("Book with ID %s not found",
                        bookDTO.getId())));
    }

    public List<BookDTO> updateBooks(List<BookDTO> bookDTOs) {
        if (bookDTOs == null || bookDTOs.isEmpty()) {
            throw new BadRequestException("Book list cannot be empty.");
        }

        for (BookDTO bookDTO : bookDTOs) {
            validateBookDTO(bookDTO);
        }

        List<Book> books = bookDTOs.stream()
                .map(bookDTO -> bookRepository.findById(bookDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with ID %s not found",
                                bookDTO.getId())))).toList();

        return DTOMapper.toBookDtoList(bookRepository.saveAll(books));
    }

    private void validateBookDTO(BookDTO bookDTO) {
        if (bookDTO.getId() == null) {
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

    private void validateBookDTOAdd(BookDTO bookDTO) {
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
        bookToUpdate.setAuthors(bookDTO.getAuthors().stream().map(DTOMapper::toAuthorEntity).toList());
        bookToUpdate.setGenres(bookDTO.getGenres());
        bookToUpdate.setDescription(bookDTO.getDescription());
    }
}
