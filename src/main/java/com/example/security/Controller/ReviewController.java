package com.example.security.Controller;

import com.example.security.DTO.request.ReviewDTO;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class ReviewController {
    private final IReviewService reviewService;
    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/{bookId}/user/{userId}/reviews/create")
    public ResponseEntity<Object> createReview(@Valid @PathVariable long bookId,
                                               @PathVariable long userId,
                                               @RequestBody ReviewDTO reviewDTO){

        ReviewDTO response = reviewService.createReview(bookId, userId, reviewDTO);
        return ResponseHandler.generateResponse("Review Successfully Created", HttpStatus.CREATED, response);
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<Object> getReviewsByBookId(@PathVariable(value = "bookId") long bookId){
        List<ReviewDTO> reviews = reviewService.getReviewByBookId(bookId);
        return  ResponseHandler.generateResponse("Fetch All Successfully", HttpStatus.OK, reviews);
    }

    @PutMapping("/review/{reviewId}/user/{userId}")
    public ResponseEntity<Object> updateComment(@Valid @PathVariable(value = "reviewId") long reviewId,
                                                @PathVariable(value = "userId") long userId,
                                                @RequestBody ReviewDTO reviewDTO){
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, userId, reviewDTO);
        return ResponseHandler.generateResponse("Updated Successfully",HttpStatus.OK,updatedReview);
    }

    @DeleteMapping("/review/{reviewId}/user/{userId}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "reviewId") long reviewId,
                                               @PathVariable(value = "userId") long userId){
           reviewService.deleteReview(reviewId, userId);
        return ResponseHandler.generateResponse("Resource Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
