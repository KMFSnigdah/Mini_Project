package com.example.security.Controller;

import com.example.security.DTO.request.LogInRequestDTO;
import com.example.security.DTO.response.AuthenticationResponseDTO;
import com.example.security.DTO.response.LogInResponseDTO;
import com.example.security.DTO.request.RegisterRequestDTO;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IAuthenticationService;
import com.example.security.service.impl.LogoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private IAuthenticationService authenticationService;
    private LogoutService logoutService;

    // For Registration account
    // http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDTO request){
        AuthenticationResponseDTO responseDTO = authenticationService.register(request);
        return ResponseHandler.generateResponse("Account Create SuccessFully", HttpStatus.CREATED,responseDTO);
    }

    // For Login account
    // http://localhost:8080/api/v1/auth/login
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody LogInRequestDTO request){
        LogInResponseDTO response = authenticationService.authenticate(request);
        return ResponseHandler.generateResponse("Login account Successfully", HttpStatus.OK,response);
    }

}
