package com.github.percivalgebashe.assignment_5_application2.mapper;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookCoverDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.BookDTO;
import com.github.percivalgebashe.assignment_5_application2.dto.PublisherDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import com.github.percivalgebashe.assignment_5_application2.entity.Publisher;

import java.util.stream.Collectors;

public class DTOMapper {

    public static BookDTO toBookDto(Book book) {
        return BookDTO.builder()
                .id(book.getBookId())
                .authors(book.getAuthors().stream().map(DTOMapper::toAuthorDto).collect(Collectors.toList()))
                .title(book.getTitle())
                .publishedDate(book.getPublishedDate())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .genres(book.getGenres())
                .publishers(book.getPublishers().stream().map(DTOMapper::toPublisherDto).collect(Collectors.toSet()))
                .build();
    }

    public static Book toBookEntity(BookDTO bookDto) {
        Book book = new Book();
        book.setBookId(bookDto.getId());
        book.setAuthors(bookDto.getAuthors().stream().map(DTOMapper::toAuthorEntity).collect(Collectors.toList()));
        book.setTitle(bookDto.getTitle());
        book.setPublishedDate(bookDto.getPublishedDate());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setGenres(bookDto.getGenres());
        book.setPublishers(bookDto.getPublishers().stream().map(DTOMapper::toPublisherEntity).collect(Collectors.toSet()));
        return book;
    }

    public static AuthorDTO toAuthorDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getAuthorId())
                .name(author.getName())
                .build();
    }

    public static Author toAuthorEntity(AuthorDTO authorDto) {
        Author author = new Author();
        author.setAuthorId(authorDto.getId());
        author.setName(authorDto.getName());
        return author;
    }

    public static PublisherDTO toPublisherDto(Publisher publisher) {
        return PublisherDTO.builder()
                .id(publisher.getPublisherId())
                .name(publisher.getName())
                .build();
    }

    public static Publisher toPublisherEntity(PublisherDTO publisherDto) {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(publisherDto.getId());
        publisher.setName(publisherDto.getName());
        return publisher;
    }

    public static BookCoverDTO toBookCoverDto(BookCover bookCover) {
        return BookCoverDTO.builder()
                .bookId(bookCover.getBookId())
                .imagePath(bookCover.getImagePath())
                .build();
    }

    public static BookCover toBookCoverEntity(BookCoverDTO bookCoverDto) {
        BookCover bookCover = new BookCover();
        bookCover.setBookId(bookCoverDto.getBookId());
        bookCover.setImagePath(bookCoverDto.getImagePath());
        return bookCover;
    }
}