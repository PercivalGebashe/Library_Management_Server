package com.github.percivalgebashe.assignment_5_application2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_covers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookCover {

    @Id
    @Column(name = "book_id") // Using book_id as Primary Key
    private String bookId;

    @OneToOne
    @MapsId // Ensures bookId matches the Book's primary key
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @Column(nullable = false, unique = true)
    private String imagePath; // Path to the book cover image
}
