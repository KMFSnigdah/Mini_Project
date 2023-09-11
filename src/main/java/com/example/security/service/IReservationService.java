package com.example.security.service;

public interface IReservationService {
    void reserve(long userId, long bookId);
    void cancelReservation(long userId, long bookId);
}
