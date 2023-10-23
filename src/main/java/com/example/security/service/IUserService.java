package com.example.security.service;

import com.example.security.DTO.response.ResponseBook;
import com.example.security.DTO.response.UserAllInfoDto;
import com.example.security.DTO.response.UserResponseDTO;
import com.example.security.entity.User;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserResponseDTO getUserById(long id);
    public List<UserAllInfoDto> getAllUsers();
    Set<ResponseBook> getListofBorrowedBook(User user, long id);
    List<ResponseBook> getListCurrentlyBorrowBookByUserId(User user, long id);
}