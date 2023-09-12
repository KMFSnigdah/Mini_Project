package com.example.security.Controller;

import com.example.security.DTO.response.HistoryResponseDTO;
import com.example.security.entity.User;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IUserHistoryService;
import com.example.security.service.IUserService;
import com.example.security.service.impl.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserHistoryController {
    private final IUserHistoryService userHistoryService;
    private final IUserService userService;
    private final AuthenticationService authenticationService;
    public UserHistoryController(IUserHistoryService userHistoryService, IUserService userService, AuthenticationService authenticationService) {
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
    @GetMapping("/{userId}/history")
    public ResponseEntity<Object> getHistoryByUserId(@PathVariable long userId) {
        User user = authenticationService.getAuthenticatedUser();
        List<HistoryResponseDTO> response = userHistoryService.getHistoryByUserId(user, userId);
        return ResponseHandler.generateResponse("Fetch History Successfully", HttpStatus.OK, response);
    }
}
