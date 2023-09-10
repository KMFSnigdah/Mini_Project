package com.example.security.service.impl;

import com.example.security.DTO.request.CreateBorrowDTO;
import com.example.security.DTO.response.ResponseBorrowDTO;
import com.example.security.entity.Book;
import com.example.security.entity.BorrowBook;
import com.example.security.entity.User;
import com.example.security.entity.UserHistory;
import com.example.security.exception.CustomeException;
import com.example.security.exception.ResourceNotFoundException;
import com.example.security.repository.BookRepository;
import com.example.security.repository.BorrowBookRepository;
import com.example.security.repository.UserHistoryRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.IBookBorrow;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookBorrow implements IBookBorrow {
   private final BorrowBookRepository borrowBookRepository;
   private final UserRepository userRepository;
   private final BookRepository bookRepository;
   private final UserHistoryRepository userHistoryRepository;

    public BookBorrow(BorrowBookRepository borrowBookRepository, UserRepository userRepository, BookRepository bookRepository, UserHistoryRepository userHistoryRepository) {
        this.borrowBookRepository = borrowBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userHistoryRepository = userHistoryRepository;
    }

    @Override
    public ResponseBorrowDTO borrowBook(long userId, long bookId, CreateBorrowDTO createBorrowDTO) {
        // Check is User available or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        // Check is Book exists or not
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", "id", bookId));

        // Check is Book available or not
        if (!book.isAvailable()) {
            throw new CustomeException(HttpStatus.NOT_FOUND, "This book is not available now.");
        }
        // Update Book status
        book.setAvailable(false);
        // Save in Database
        bookRepository.save(book);
        borrowBookRepository.save(new BorrowBook(new Date(), createBorrowDTO.getDueDate(), book, user));

        // Added this event in User History
        UserHistory history = new UserHistory();
        history.setBorrowDate(new Date());
        history.setDueDate(createBorrowDTO.getDueDate());
        history.setUser(user);
        history.setBook(book);
        userHistoryRepository.save(history);

        // Map entity to DTO
        return new ResponseBorrowDTO(
                book.getTitle(),
                user.getFirstName(),
                new Date(),
                createBorrowDTO.getDueDate()
        );
    }

    @Override
    public void returnBook(long bookId) {
        // Check is Book exists or not
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", "id", bookId));

        if (book.isAvailable()) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "This book is not borrowed");
        }
        // Update Book status
        book.setAvailable(true);
        // Save in Database
        bookRepository.save(book);
        //Added this event in User History
        UserHistory history = userHistoryRepository.findByBookId(book.getId());
        history.setReturnedDate(new Date());
        userHistoryRepository.save(history);

        borrowBookRepository.deleteByBookId(bookId);
    }
}
