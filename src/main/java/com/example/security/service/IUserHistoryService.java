package com.example.security.service;

import com.example.security.DTO.response.HistoryResponseDTO;
import com.example.security.entity.User;

import java.util.List;

public interface IUserHistoryService {
    List<HistoryResponseDTO> getHistoryByUserId(User user, long userId);
}
