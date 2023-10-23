package com.example.security.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBook {
    private String bookTitle;
    private String author;
    private Date borrowDate;
    private Date dueDate;
}
