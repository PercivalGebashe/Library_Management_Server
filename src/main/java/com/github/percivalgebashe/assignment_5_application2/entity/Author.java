package com.github.percivalgebashe.assignment_5_application2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Author implements Serializable {

    @Id
    @Column(nullable = false, unique = true, length = 100)
    private String authorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    )
    private List<Book> books;

    public void generateAuthorId() {
        this.authorId = name.replaceAll("\\s+", "") + "_" + birthDate.getYear();
    }
}