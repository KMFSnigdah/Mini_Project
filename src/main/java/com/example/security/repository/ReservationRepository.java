package com.example.security.repository;

import com.example.security.entity.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<BookReservation, Long> {
    boolean existsByBookIdAndUserId(long bookId, long userId);
    Optional<BookReservation> findByBookIdAndUserId(long bookId, long userId);
}
