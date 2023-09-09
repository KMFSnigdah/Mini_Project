package com.example.security.service;

import com.example.security.DTO.request.ReviewDTO;

import java.util.List;

public interface IReviewService {
    ReviewDTO createReview(long bookId, long userId, ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewByBookId(long bookId);

    ReviewDTO updateReview(long reviewId, long userId, ReviewDTO reviewDTO);
    void deleteReview(long reviewId, long userId);
}
