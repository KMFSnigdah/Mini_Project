package com.example.security.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponseDTO {
    private String bookName;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
}
