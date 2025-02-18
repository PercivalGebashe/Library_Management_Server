package com.github.percivalgebashe.assignment_5_application2.dto;

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
    private List<BookDTO> books;

    public void generateAuthorId() {
        id = name.replaceAll("\\s+", "") + "_" + birthDate.getYear();
    }
}
