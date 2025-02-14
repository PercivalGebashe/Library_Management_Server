package com.github.percivalgebashe.assignment_5_application2.dto;

import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorDTO {

    private Long id;

    private String name;

    public Author toEntity() {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        return author;
    }
}
