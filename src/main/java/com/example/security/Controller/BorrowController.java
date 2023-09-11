package com.example.security.Controller;

import com.example.security.DTO.request.CreateBorrowDTO;
import com.example.security.DTO.response.ResponseBorrowDTO;
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

    @PostMapping("/users/{userId}/books/{bookId}/borrow")
    public ResponseEntity<Object> borrowBook(@PathVariable("userId") long userId,
                                             @PathVariable("bookId") long bookId,
                                             @Valid @RequestBody CreateBorrowDTO requestDto) {
        ResponseBorrowDTO response = bookBorrow.borrowBook(userId, bookId, requestDto);
        return ResponseHandler.generateResponse("Borrowed This Book Successfully", HttpStatus.OK, response);
    }
    @DeleteMapping("/{bookId}/return")
    public ResponseEntity<Object> returnBook(@PathVariable long bookId){
        bookBorrow.returnBook(bookId);
        return ResponseHandler.generateResponse("Returned This Book Successfully", HttpStatus.OK);
    }

}
