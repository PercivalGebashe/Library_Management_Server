package com.github.percivalgebashe.assignment_5_application2.dto;

import com.github.percivalgebashe.assignment_5_application2.util.IdGenerator;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;

@Data
@Builder
public class BookDTO {
    private String id;
    private String authors;
    private String title;
    private LocalDate publishedDate;
    private String description;
    private String isbn;
    private String genres;
    private String publishers;

    public void generateBookId() {
        if (authors != null && !authors.isEmpty()) {
             id = IdGenerator.generateBookId(Arrays.stream(authors.split(",")).toList()
             ,title, publishedDate);
        }
    }
}