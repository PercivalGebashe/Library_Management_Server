package com.github.percivalgebashe.assignment_5_application2.dto;

import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import com.github.percivalgebashe.assignment_5_application2.entity.Book;
import com.github.percivalgebashe.assignment_5_application2.entity.Genre;
import com.github.percivalgebashe.assignment_5_application2.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    private Long book_id;

    private List<Author> authors;

    private String title;

    private LocalDate publishedDate;

    private Set<Publisher> publishers;

    private String description;

    private String isbn;

    private Set<Genre> genres;

    public Book toBookEntity() {
        Book book = new Book();
        book.setId(book_id);
        book.setAuthors(authors);
        book.setTitle(title);
        book.setPublishedDate(publishedDate);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setGenres(genres);
        return book;
    }
}
