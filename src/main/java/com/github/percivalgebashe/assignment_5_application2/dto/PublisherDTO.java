package com.github.percivalgebashe.assignment_5_application2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class PublisherDTO {
    private String id;
    private String name;
    private int foundedYear;

    public Optional<String> generatePublisherId() {
        return Optional.of(name.replaceAll("\\s+", "") + "_" + foundedYear);
    }
}
