package com.example.security.repository;

import com.example.security.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    List<UserHistory> findByUserId(long userId);
    @Query("SELECT uh FROM UserHistory uh WHERE uh.book.id = :bookId ORDER BY uh.id DESC LIMIT 1")
    UserHistory findByBookId(long bookId);
}
