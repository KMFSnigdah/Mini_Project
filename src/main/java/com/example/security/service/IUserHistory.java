package com.example.security.service;

import com.example.security.DTO.response.HistoryResponseDTO;

import java.util.List;

public interface IUserHistory {
    List<HistoryResponseDTO> getHistoryByUserId(long userId);
}
