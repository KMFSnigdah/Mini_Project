package com.example.security.DTO.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private long id;
    private String title;
    private String author;
    private String description;
    private boolean isAvailable;
    private BigDecimal rating;
}
