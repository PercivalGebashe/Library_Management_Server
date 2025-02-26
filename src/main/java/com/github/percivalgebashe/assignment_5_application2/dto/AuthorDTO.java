package com.github.percivalgebashe.assignment_5_application2.dto;

import com.github.percivalgebashe.assignment_5_application2.util.IdGenerator;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class AuthorDTO {
    private String id;
    private String name;
    private LocalDate birthDate;
    private String biography;
    private List<BookDTO> books;

    public void generateAuthorId() {
        id = IdGenerator.generateAuthorId(name, birthDate);
    }
}
