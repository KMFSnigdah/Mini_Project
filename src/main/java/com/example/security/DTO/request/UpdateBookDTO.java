package com.example.security.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDTO {

    @NotEmpty
    @Size(min = 2, message = "Title should have at least 2 character")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "Author should have at least 2 character")
    private String author;

    @NotEmpty
    @Size(min = 2, message = "Description should have at least 2 character")
    private String description;

    @NotNull
    private boolean isAvailable;
}
