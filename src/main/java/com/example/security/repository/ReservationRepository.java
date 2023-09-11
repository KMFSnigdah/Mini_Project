package com.example.security.repository;

import com.example.security.entity.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<BookReservation, Long> {
    boolean existsByBookIdAndUserId(long bookId, long userId);
}
