package com.example.security.service;

import com.example.security.DTO.request.LogInRequestDto;
import com.example.security.DTO.response.AuthenticationResponseDTO;
import com.example.security.DTO.response.LogInResponseDTO;
import com.example.security.DTO.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {
    AuthenticationResponseDTO register(RegisterRequest request);

    LogInResponseDTO authenticate(LogInRequestDto request);

    String extractTokenFromRequest(HttpServletRequest request);
}
