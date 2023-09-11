package com.example.security.Controller;

import com.example.security.DTO.request.ReviewDTO;
import com.example.security.DTO.response.ResponseBook;
import com.example.security.DTO.response.UserResponseDTO;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getReviewsByBookId(@PathVariable(value = "userId") long userId){
        UserResponseDTO response = userService.getUserById(userId);
        return  ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/users/{userId}/books")
    public ResponseEntity<Object> getBooksByUserId(@PathVariable(value = "userId") long userId){
        List<ResponseBook> response = userService.getListofBorrowedBook(userId);
        return  ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/users/{userId}/borrowed-books")
    public ResponseEntity<Object> getCurrentlyBorrowBooksByUserId(@PathVariable(value = "userId") long userId){
        List<ResponseBook> response = userService.getListCurrentlyBorrowBookByUserId(userId);
        return  ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }
}
