package com.example.security.service.impl;

import com.example.security.DTO.request.ReviewDTO;
import com.example.security.entity.Book;
import com.example.security.entity.BookReview;
import com.example.security.entity.User;
import com.example.security.exception.ResourceNotFoundException;
import com.example.security.repository.BookRepository;
import com.example.security.repository.ReviewRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.IReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository, ModelMapper mapper) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ReviewDTO createReview(long bookId, long userId, ReviewDTO reviewDTO) {
        // DTO to Entity
        BookReview review = mapper.map(reviewDTO, BookReview.class);

        // Check is book available or not
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        // Check is review available or not
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        // Set Book and User in Review Entity
        review.setBook(book);
        review.setUser(user);

        // Save Review Entity
        BookReview response = reviewRepository.save(review);
        return mapper.map(response, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewByBookId(long bookId) {
        // Check is book available or not
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        // Retrieve All Reviews by Book ID
        List<BookReview> bookReviews = reviewRepository.findByBookId(bookId);
        // Map Reviews to DTO & Return
        return bookReviews.stream().map(review -> mapper.map(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReview(long reviewId, long userId, ReviewDTO reviewDTO) {
        // Check is User available or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        // Check is review available or not
        BookReview review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review", "id", reviewId));

        if(review.getUser().getId() != user.getId()){
            throw new ResourceNotFoundException("dfdfdfdsfdfsd", "id", reviewId);
        }

        //Update Comment
        review.setRating(reviewDTO.getRating());
        review.setReview(reviewDTO.getReview());
        // Update in Database
        BookReview updatedReview = reviewRepository.save(review);

        return mapper.map(updatedReview, ReviewDTO.class);
    }

    @Override
    public void deleteReview(long reviewId, long userId) {
        // Check is User available or not
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        // Check is review available or not
        BookReview review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review", "id", reviewId));

        if(review.getUser().getId() != user.getId()){
            throw new ResourceNotFoundException("dfdfdfdsfdfsd", "id", reviewId);
        }

        reviewRepository.delete(review);
    }
}
