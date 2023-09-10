package com.example.security.repository;

import com.example.security.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findByBookId(long postId);

    // Custom method to check if a user has already reviewed a book
    boolean existsByBookIdAndUserId(long bookId, long userId);
}
