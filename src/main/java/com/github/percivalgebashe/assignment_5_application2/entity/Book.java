package com.github.percivalgebashe.assignment_5_application2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Book implements Serializable {

    @Id
    @Column(nullable = false, unique = true, length = 100)
    private String bookId;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String genres;

    @Column(nullable = false)
    private String publishers;

    @PrePersist
    private void generateId() {
        if(bookId == null) {
            generateBookId();
        }
    }

    public void generateBookId() {
        if (authors != null && !authors.isEmpty()) {
            String authorNames = authors.stream()
                    .map(Author::getName)
                    .collect(Collectors.joining("_"));

            this.bookId = authorNames.replaceAll("\\s+", "") + "_" + title.replaceAll("\\s+", "");
        }
    }
}