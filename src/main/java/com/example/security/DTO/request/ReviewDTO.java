package com.example.security.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotEmpty(message = "Ratting can't be empty")
    @Min(value = 0) // Minimum value allowed (0.0)
    @Max(value = 5) // Maximum value allowed (5.0)
    private BigDecimal rating;

    @NotEmpty(message = "Review can't be empty")
    private String review;
}
