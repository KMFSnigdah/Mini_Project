package com.example.security.repository;

import com.example.security.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByIsDeletedFalse(Pageable pageable);
}
