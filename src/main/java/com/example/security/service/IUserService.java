package com.example.security.service;

import com.example.security.DTO.response.ResponseBook;
import com.example.security.DTO.response.UserResponseDTO;

import java.util.List;

public interface IUserService {
    UserResponseDTO getUserById(long id);
    List<ResponseBook> getListofBorrowedBook(long id);
    List<ResponseBook> getListCurrentlyBorrowBookByUserId(long id);
}