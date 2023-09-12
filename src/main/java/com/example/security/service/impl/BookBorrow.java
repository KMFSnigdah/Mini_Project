package com.example.security.service.impl;

import com.example.security.DTO.request.CreateBorrowDTO;
import com.example.security.DTO.response.ResponseBorrowDTO;
import com.example.security.entity.*;
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
        User user = getUserById(userId);
        Book book = getBookById(bookId);
        checkBookAvailability(book);

        // Check if Delete or not already
        if (book.isDeleted()) throw new ResourceNotFoundException("Book", "id", bookId);

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
    public void returnBook(long bookId, long userId) {
        // Check is User available or not
        User user = getUserById(userId);
        // Check is Book exists or not
        Book book = getBookById(bookId);

        // Check if Delete or not already
        if (book.isDeleted()) throw new ResourceNotFoundException("Book", "id", bookId);

        if (book.isAvailable()) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "This book is not borrowed");
        }
        if(book.getBookBorrowing().getUser().getId()!=userId){
            throw new CustomeException(HttpStatus.BAD_REQUEST, "This book is not borrowed by this user");
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

    private User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    private Book getBookById(long bookId) {
        return bookRepository.findById(bookId)
                .filter(book -> !book.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
    }

    private void checkBookAvailability(Book book) {
        if (!book.isAvailable()) {
            throw new CustomeException(HttpStatus.NOT_FOUND, "This book is not available now.");
        }
    }

}
