package com.example.security.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBorrowDTO {
    @NotNull(message = "DueDate should not be null or empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
}
