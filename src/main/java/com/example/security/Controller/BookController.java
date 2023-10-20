package com.example.security.Controller;

import com.example.security.DTO.request.CreateBookDTO;
import com.example.security.DTO.request.UpdateBookDTO;
import com.example.security.DTO.response.BookResponseDTO;
import com.example.security.entity.Book;
import com.example.security.response.CustomResponse;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IBookService;
import com.example.security.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.example.security.utils.Constants.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final IBookService bookService;
    private final AuthenticationService authenticationService;

    public BookController(IBookService bookService, AuthenticationService authenticationService) {
        this.bookService = bookService;
        this.authenticationService = authenticationService;
    }

    // Create a new Book
    // http://localhost:8080/books/create
    @PostMapping("/create")
    public ResponseEntity<Object> createBook(@Valid @RequestBody CreateBookDTO bookRequestDTO) {
        BookResponseDTO response = bookService.createBook(bookRequestDTO);
        return ResponseHandler.generateResponse("Create Book Successfully", HttpStatus.CREATED, response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable long id){
        BookResponseDTO responseDTO = bookService.getBookById(id);
        return ResponseHandler.generateResponse("Fetch Book Successfully", HttpStatus.OK, responseDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateBook(@Valid @RequestBody UpdateBookDTO updateBookDTO,
                                             @PathVariable long id){
        BookResponseDTO response = bookService.updateBook(id, updateBookDTO);
        return ResponseHandler.generateResponse("Update Book Successfully", HttpStatus.OK, response);
    }

    // http://localhost:8080/books/getAll?pageNo=1&pageSize=10&sortBy=title&sortDir=asc
    @GetMapping("/all")
    public ResponseEntity<Object> getAllBooks(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){

        CustomResponse customResponse = bookService.getAllBooks(pageNo, pageSize, sortBy, sortDir);
        return ResponseHandler.generateResponse("Fetch All Data Successfully", HttpStatus.OK,customResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
        return ResponseHandler.generateResponse("Delete Book Successfully", HttpStatus.OK);
    }
}
