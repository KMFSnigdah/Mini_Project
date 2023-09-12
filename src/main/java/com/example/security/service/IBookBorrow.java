package com.example.security.service;

import com.example.security.DTO.request.CreateBookDTO;
import com.example.security.DTO.request.CreateBorrowDTO;
import com.example.security.DTO.response.ResponseBorrowDTO;

public interface IBookBorrow {
    ResponseBorrowDTO borrowBook(long userId, long bookId, CreateBorrowDTO createBorrowDTO);
    void returnBook(long bookId, long userId);
}
