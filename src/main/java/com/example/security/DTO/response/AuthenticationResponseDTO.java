package com.example.security.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private long userId;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private String address;
    private String token;
}
