package com.example.security.Controller;

import com.example.security.DTO.response.ResponseBook;
import com.example.security.DTO.response.UserResponseDTO;
import com.example.security.entity.User;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IUserService;
import com.example.security.service.impl.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final AuthenticationService authenticationService;

    public UserController(IUserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserByUserId(@PathVariable(value = "userId") long userId){
        UserResponseDTO response = userService.getUserById(userId);
        return  ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/{userId}/books")
    public ResponseEntity<Object> getBooksByUserId(@PathVariable(value = "userId") long userId){
        User user = authenticationService.getAuthenticatedUser();
        Set<ResponseBook> response = userService.getListofBorrowedBook(user, userId);
        return  ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/{userId}/borrowed-books")
    public ResponseEntity<Object> getCurrentlyBorrowBooksByUserId(@PathVariable(value = "userId") long userId){
        User user = authenticationService.getAuthenticatedUser();
        List<ResponseBook> response = userService.getListCurrentlyBorrowBookByUserId(user,userId);
        return  ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }
}
