package com.example.security.Controller;

import com.example.security.DTO.request.CreateBorrowDTO;
import com.example.security.DTO.response.ResponseBorrowDTO;
import com.example.security.entity.User;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IBookBorrow;
import com.example.security.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BorrowController {

    private  final IBookBorrow bookBorrow;
    private final AuthenticationService authenticationService;

    public BorrowController(IBookBorrow bookBorrow, AuthenticationService authenticationService) {
        this.bookBorrow = bookBorrow;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<Object> borrowBook(@PathVariable("bookId") long bookId,
                                             @Valid @RequestBody CreateBorrowDTO requestDto) {
        User user = authenticationService.getAuthenticatedUser();
        ResponseBorrowDTO response = bookBorrow.borrowBook(user.getId(), bookId, requestDto);
        return ResponseHandler.generateResponse("Borrowed This Book Successfully", HttpStatus.OK, response);
    }
    @DeleteMapping("/{bookId}/return")
    public ResponseEntity<Object> returnBook(@PathVariable long bookId){
        User user = authenticationService.getAuthenticatedUser();
        bookBorrow.returnBook(bookId, user.getId());
        return ResponseHandler.generateResponse("Returned This Book Successfully", HttpStatus.OK);
    }

}
