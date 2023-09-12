package com.example.security.Controller;

import com.example.security.entity.User;
import com.example.security.response.ResponseHandler;
import com.example.security.service.IReservationService;
import com.example.security.service.impl.AuthenticationService;
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
    private final AuthenticationService authenticationService;
    public ReservationController(IReservationService reservationService, AuthenticationService authenticationService) {
        this.reservationService = reservationService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{bookId}/reserve")
    public ResponseEntity<Object> reserveBook(@PathVariable long bookId) {
        User user = authenticationService.getAuthenticatedUser();
        reservationService.reserve(user.getId(), bookId);
        return ResponseHandler.generateResponse("Book Reserve Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}/cancel-reservation")
    public ResponseEntity<Object> cancelReserveBook(@PathVariable long bookId) {
        User user = authenticationService.getAuthenticatedUser();
        reservationService.cancelReservation(user.getId(), bookId);
        return ResponseHandler.generateResponse("Book Reserve Cancel Successfully", HttpStatus.OK);
    }
}
