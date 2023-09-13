package com.example.security.Controller;

import com.example.security.DTO.request.LogInRequestDTO;
import com.example.security.DTO.response.AuthenticationResponseDTO;
import com.example.security.DTO.response.LogInResponseDTO;
import com.example.security.DTO.request.RegisterRequestDTO;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AuthenticationController {

    private IAuthenticationService authenticationService;

    // For Registration account
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDTO request){
        AuthenticationResponseDTO responseDTO = authenticationService.register(request);
        return ResponseHandler.generateResponse("Account Create SuccessFully", HttpStatus.CREATED,responseDTO);
    }

    // For Login account
    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody LogInRequestDTO request){
        LogInResponseDTO response = authenticationService.authenticate(request);
        return ResponseHandler.generateResponse("Login account Successfully", HttpStatus.OK,response);
    }

}
