package com.example.security.Controller;

import com.example.security.DTO.response.HistoryResponseDTO;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IUserHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class UserHistoryController {
    private final IUserHistory userHistoryService;
    public UserHistoryController(IUserHistory userHistoryService) {
        this.userHistoryService = userHistoryService;
    }
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<Object> getHistoryByUserId(@PathVariable long userId) {
        List<HistoryResponseDTO> response = userHistoryService.getHistoryByUserId(userId);
        return ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }
}
