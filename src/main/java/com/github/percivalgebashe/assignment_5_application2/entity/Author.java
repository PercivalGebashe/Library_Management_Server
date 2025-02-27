package com.github.percivalgebashe.assignment_5_application2.entity;

import com.github.percivalgebashe.assignment_5_application2.util.IdGenerator;
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
    private String authorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @PrePersist
    private void generateId() {
        if(authorId == null) {
            generateAuthorId();
        }
    }

    public void generateAuthorId() {
        this.authorId = IdGenerator.generateAuthorId(name, birthDate);
    }
}