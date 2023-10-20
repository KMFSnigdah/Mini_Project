package com.example.security.DTO.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @DecimalMin(value = "0.1", inclusive = true)
    @DecimalMax(value = "9.9", inclusive = true)
    private BigDecimal rating;

    @NotEmpty(message = "Review can't be empty")
    private String review;


}
