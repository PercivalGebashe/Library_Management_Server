package com.github.percivalgebashe.assignment_5_application2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "publishers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Publisher implements Serializable {

    @Id
    @Column(nullable = false, unique = true, length = 100)
    private String publisherId;

    @ManyToMany( mappedBy= "publishers")
    private Set<Book> books;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int foundedYear;

    @Column(columnDefinition = "TEXT")
    private String headquartersLocation;

    @PrePersist
    private void generateId() {
        if(publisherId == null) {
            generatePublisherId();
        }
    }

    public void generatePublisherId() {
        this.publisherId = name.replaceAll("\\s+", "") + "_" + foundedYear;
    }
}