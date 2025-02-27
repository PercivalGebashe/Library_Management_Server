package com.github.percivalgebashe.assignment_5_application2.mapper;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static BookDTO toBookDto(Book book) {
        return BookDTO.builder()
                .id(book.getBookId())
                .authors(book.getAuthors())
                .title(book.getTitle())
                .publishers(book.getPublishers())
                .publishedDate(book.getPublishedDate())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .genres(book.getGenres())
                .build();
    }

    public static List<BookDTO> toBookDtoList(List<Book> books) {
        return books.stream().map(DTOMapper::toBookDto).collect(Collectors.toList());
    }

    public static Book toBookEntity(BookDTO bookDto) {
        Book book = new Book();
        book.setBookId(bookDto.getId());
        book.setAuthors(bookDto.getAuthors());
        book.setTitle(bookDto.getTitle());
        book.setPublishers(bookDto.getPublishers());
        book.setPublishedDate(bookDto.getPublishedDate());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setGenres(bookDto.getGenres());
        return book;
    }

    public static List<Book> toBooKEntityList(List<BookDTO> bookDTOS) {
        return bookDTOS.stream().map(DTOMapper::toBookEntity).collect(Collectors.toList());
    }

    public static AuthorDTO toAuthorDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getAuthorId())
                .name(author.getName())
                .build();
    }

    public static List<AuthorDTO> toAuthorDtoList(List<Author> authors) {
        return authors.stream().map(DTOMapper::toAuthorDto).collect(Collectors.toList());
    }

    public static Author toAuthorEntity(AuthorDTO authorDto) {
        Author author = new Author();
        author.setAuthorId(authorDto.getId());
        author.setName(authorDto.getName());
        return author;
    }

    public static List<Author> toAuthorEntityList(List<AuthorDTO> authorDTOS) {
        return authorDTOS.stream().map(DTOMapper::toAuthorEntity).collect(Collectors.toList());
    }


    public static BookCoverDTO toBookCoverDto(BookCover bookCover) {
        return BookCoverDTO.builder()
                .bookId(bookCover.getBookId())
                .imagePath(bookCover.getImagePath())
                .build();
    }

    public static List<BookCoverDTO> toBookCoverDtoList(List<BookCover> bookCovers) {
        return bookCovers.stream().map(DTOMapper::toBookCoverDto).collect(Collectors.toList());
    }

    public static BookCover toBookCoverEntity(BookCoverDTO bookCoverDto) {
        BookCover bookCover = new BookCover();
        bookCover.setBookId(bookCoverDto.getBookId());
        bookCover.setImagePath(bookCoverDto.getImagePath());
        return bookCover;
    }

    public static List<BookCover> toBookCoverEntityList(List<BookCoverDTO> bookCoverDTOS) {
        return bookCoverDTOS.stream().map(DTOMapper::toBookCoverEntity).collect(Collectors.toList());
    }
}