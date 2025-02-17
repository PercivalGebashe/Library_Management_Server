package com.github.percivalgebashe.assignment_5_application2.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AuthorDTO {
    private String id;
    private String name;
    private List<BookDTO> books;
}
