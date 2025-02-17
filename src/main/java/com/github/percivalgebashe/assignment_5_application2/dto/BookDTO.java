package com.github.percivalgebashe.assignment_5_application2.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class BookDTO {
    private String id;
    private List<AuthorDTO> authors;
    private String title;
    private LocalDate publishedDate;
    private String description;
    private String isbn;
    private String genres;
    private Set<PublisherDTO> publishers;

    public Optional<String> generateBookId() {
        if (authors != null && !authors.isEmpty()) {
            String authorNames = authors.stream()
                    .map(AuthorDTO::getName)
                    .collect(Collectors.joining("_"));

            return Optional.of(authorNames.replaceAll("\\s+", "") + "_" + title.replaceAll("\\s+", ""));
        }
        return null;
    }
}