package com.example.security.service.impl;

import com.example.security.entity.Book;
import com.example.security.entity.BookReservation;
import com.example.security.entity.User;
import com.example.security.exception.CustomeException;
import com.example.security.exception.ResourceNotFoundException;
import com.example.security.repository.BookRepository;
import com.example.security.repository.ReservationRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void reserve(long userId, long bookId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);

        // Check reservation and availability, throw exceptions if conditions not met
        checkReservationAndAvailability(userId, bookId, book);
        // Check if user already borrowed this book
        if(book.getBookBorrowing().getUser().getId() == userId){
            throw new CustomeException(HttpStatus.CONFLICT, "You have already borrowed this book");
        }
        // Create a new reservation and save it in the database
        BookReservation reservation = createReservation(book, user);
        reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(long userId, long bookId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);

        // Find the user's reservation for the specified book, throw exception if not found
        //BookReservation reservation = findUserReservation(book, userId);
        BookReservation reservation = book.getReservation().stream()
                .filter(r -> r.getUser().getId() == userId)
                .findFirst()
                .orElseThrow(() -> new CustomeException(HttpStatus.NOT_FOUND, "This user has not reserved this book"));

        reservationRepository.delete(reservation);
    }


    // Check reservation, availability, and deleted status, and throw exceptions if conditions not met
    private void checkReservationAndAvailability(long userId, long bookId, Book book) {
        if (reservationRepository.existsByBookIdAndUserId(bookId, userId)) {
            throw new CustomeException(HttpStatus.CONFLICT, "This book is already reserved by you");
        }
        if (book.isAvailable()) {
            throw new CustomeException(HttpStatus.CONFLICT, "This book is already available. You can borrow it directly");
        }
        if (book.isDeleted()) {
            throw new ResourceNotFoundException("Book", "id", bookId);
        }
    }

    // Create a new reservation object
    private BookReservation createReservation(Book book, User user) {
        BookReservation reservation = new BookReservation();
        reservation.setReservationDate(new Date());
        reservation.setBook(book);
        reservation.setUser(user);
        return reservation;
    }

    // Check if a user is available or not, and throw a ResourceNotFoundException if not
    private User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    // Check if a book exists or not, and throw a ResourceNotFoundException if not
    private Book getBookById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
    }

    // Find a user's reservation for a specific book, throw an exception if not found
    private BookReservation findUserReservation(Book book, long userId) {
        return book.getReservation().stream()
                .filter(r -> r.getUser().getId() == userId)
                .findFirst()
                .orElseThrow(() -> new CustomeException(HttpStatus.NOT_FOUND, "This user has not reserved this book"));
    }
}
