package com.example.security.Controller;

import com.example.security.DTO.request.ReviewDTO;
import com.example.security.entity.User;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IReviewService;
import com.example.security.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class ReviewController {
    private final IReviewService reviewService;
    private final AuthenticationService authenticationService;

    public ReviewController(IReviewService reviewService, AuthenticationService authenticationService) {
        this.reviewService = reviewService;
        this.authenticationService = authenticationService;
    }
    @PostMapping("/{bookId}/reviews/create")
    public ResponseEntity<Object> createReview(@PathVariable long bookId,
                                               @Valid @RequestBody ReviewDTO reviewDTO){
        User user = authenticationService.getAuthenticatedUser();
        ReviewDTO response = reviewService.createReview(bookId, user.getId(), reviewDTO);
        return ResponseHandler.generateResponse("Review Successfully Created", HttpStatus.CREATED, response);
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<Object> getReviewsByBookId(@PathVariable(value = "bookId") long bookId){
        List<ReviewDTO> reviews = reviewService.getReviewByBookId(bookId);
        return  ResponseHandler.generateResponse("Fetch All Successfully", HttpStatus.OK, reviews);
    }

    @PutMapping("/reviews/{reviewId}/update")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "reviewId") long reviewId,
                                                @Valid @RequestBody ReviewDTO reviewDTO){
        User user = authenticationService.getAuthenticatedUser();
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, user.getId(), reviewDTO);
        return ResponseHandler.generateResponse("Updated Successfully",HttpStatus.OK,updatedReview);
    }

    @DeleteMapping("/reviews/{reviewId}/delete")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "reviewId") long reviewId){
        User user = authenticationService.getAuthenticatedUser();
           reviewService.deleteReview(reviewId, user.getId());
        return ResponseHandler.generateResponse("Resource Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
