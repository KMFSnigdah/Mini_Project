package com.example.security.service;

import com.example.security.DTO.request.LogInRequestDTO;
import com.example.security.DTO.response.AuthenticationResponseDTO;
import com.example.security.DTO.response.LogInResponseDTO;
import com.example.security.DTO.request.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request);

    LogInResponseDTO authenticate(LogInRequestDTO request);

    String extractTokenFromRequest(HttpServletRequest request);
}
