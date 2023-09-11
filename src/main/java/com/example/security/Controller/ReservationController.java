package com.example.security.Controller;

import com.example.security.response.ResponseHandler;
import com.example.security.service.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class ReservationController {
    private final IReservationService reservationService;
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{bookId}/reserve/user/{userId}")
    public ResponseEntity<Object> reserveBook(@PathVariable long bookId,
                                              @PathVariable long userId) {
        reservationService.reserve(userId, bookId);
        return ResponseHandler.generateResponse("Book Reserve Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}/cancel_reservation/user/{userId}")
    public ResponseEntity<Object> cancelReserveBook(@PathVariable long bookId,
                                              @PathVariable long userId) {
        reservationService.cancelReservation(userId, bookId);
        return ResponseHandler.generateResponse("Book Reserve Cancel Successfully", HttpStatus.OK);
    }
}
