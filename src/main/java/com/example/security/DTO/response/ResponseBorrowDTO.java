package com.example.security.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBorrowDTO {

    private String bookName;
    private String borrowerName;
    private Date borrowedDate;
    private Date dueDate;

}
