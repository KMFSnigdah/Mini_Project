package com.example.security.service.impl;

import com.example.security.DTO.request.LogInRequestDTO;
import com.example.security.DTO.response.AuthenticationResponseDTO;
import com.example.security.DTO.response.LogInResponseDTO;
import com.example.security.DTO.request.RegisterRequestDTO;
import com.example.security.security.JwtService;
import com.example.security.exception.AuthenticationException;
import com.example.security.service.IAuthenticationService;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;


    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        // Check if the email already exists in the database
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Email address is already taken.");
        }

        // Set the role based on the input or throw an exception if it's not a valid role
        Role userRole;
        if ("USER".equalsIgnoreCase(request.getRole())) {
            userRole = Role.USER;
        } else if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            userRole = Role.ADMIN;
        } else {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Invalid role. Supported roles are USER and ADMIN.");
        }

        // Create a new user entity
        String userID = JwtService.generateUserID(10);
        var user = User.builder()
                .userId(userID)
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .address(request.getAddress())
                .build();

        // Save the user to the database
       User newUser = userRepository.save(user);

        // Generate JWT token and return the response
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .userId(newUser.getId())
                .email(user.getEmail())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .address(user.getAddress())
                .role(user.getRole().toString())
                .token(jwtToken)
                .build();
    }

    public LogInResponseDTO authenticate(LogInRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            // Check is user exists or not
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AuthenticationException(HttpStatus.NOT_FOUND, "User not found"));

            var jwtToken = jwtService.generateToken(user);
            return LogInResponseDTO.builder()
                    .email(user.getEmail())
                    .role(user.getRole().toString())
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            // Handle invalid credentials (wrong email or password)
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } catch (ExpiredJwtException e) {
            // Handle expired JWT tokens
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "Session has expired. Please log in again");
        }  catch (Exception e) {
            // Handle other exceptions here
            throw new AuthenticationException(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");
        }
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new AuthenticationException(HttpStatus.NOT_FOUND, "User not found"));
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}
