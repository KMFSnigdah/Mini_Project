package com.example.security.repository;

import com.example.security.entity.Book;
import com.example.security.entity.BorrowBook;
import com.example.security.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long> {
    BorrowBook findByUserAndBook(User user, Book book);
    @Modifying
    @Transactional
    @Query("DELETE FROM BorrowBook b WHERE b.book.id = :bookId")
    void deleteByBookId(@Param("bookId") long bookId);
}
