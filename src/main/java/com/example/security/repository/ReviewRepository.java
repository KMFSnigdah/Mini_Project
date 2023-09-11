package com.example.security.repository;

import com.example.security.entity.BookReview;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findByBookId(long postId);

    // Custom method to check if a user has already reviewed a book
    boolean existsByBookIdAndUserId(long bookId, long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BookReview r WHERE r.book.id = :bookId")
    void deleteReviewsForByBookId(@Param("bookId") long bookId);
}
