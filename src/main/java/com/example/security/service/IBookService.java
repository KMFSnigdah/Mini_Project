package com.example.security.service;

import com.example.security.DTO.request.CreateBookDTO;
import com.example.security.DTO.request.UpdateBookDTO;
import com.example.security.DTO.response.BookResponseDTO;
import com.example.security.response.CustomResponse;

public interface IBookService {
    BookResponseDTO createBook(CreateBookDTO book);
    BookResponseDTO updateBook(long bookId, UpdateBookDTO book);
    CustomResponse getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir);
    void deleteBook(long id);

}
