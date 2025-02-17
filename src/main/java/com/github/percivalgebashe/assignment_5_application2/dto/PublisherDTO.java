package com.github.percivalgebashe.assignment_5_application2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublisherDTO {
    private String id; // Custom ID (String) if needed
    private String name;
}
