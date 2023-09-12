package com.example.security.service;

import com.example.security.DTO.response.HistoryResponseDTO;
import com.example.security.DTO.response.UserResponseDTO;
import com.example.security.entity.User;

import java.util.List;

public interface IUserHistory {
    List<HistoryResponseDTO> getHistoryByUserId(User user, long userId);
}
