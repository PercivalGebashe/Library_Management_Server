package com.github.percivalgebashe.assignment_5_application2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Author implements Serializable {

    @Id
    @Column(nullable = false, unique = true, length = 100) // Unique ID based on name + birth year
    private String authorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate; // Added birth date to make ID unique

    @Column(columnDefinition = "TEXT")
    private String biography; // Optional biography field

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    )
    private List<Book> books;

    // Custom method to generate a unique authorId
    public void generateAuthorId() {
        this.authorId = name.replaceAll("\\s+", "") + "_" + birthDate.getYear();
    }
}