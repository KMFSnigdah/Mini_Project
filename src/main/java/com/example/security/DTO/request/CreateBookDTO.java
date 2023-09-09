package com.example.security.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBookDTO {

    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 character")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "Author should have at least 2 character")
    private String author;

    @NotEmpty
    @Size(min = 2, message = "Description should have at least 2 character")
    private String description;
}
