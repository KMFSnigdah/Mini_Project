package com.example.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "isAvailable", nullable = false)
    private boolean isAvailable = true;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "rating")
    private BigDecimal rating = BigDecimal.ZERO;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookReview> reviews = new HashSet<>();

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private BorrowBook bookBorrowing;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<UserHistory> userHistory = new HashSet<>();

    public void setRating(BigDecimal rating) {
        if (rating != null) {
            this.rating = rating;
        }
    }
}
