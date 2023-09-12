package com.example.security.utils;

public class Constants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final String MESSAGE_KEY = "message";
    public static final String STATUS_KEY = "status";
    public static final String DATA_KEY = "data";
    public static final int SUCCESS_STATUS_CODE = 200;

    // API Paths
    public static final String API_AUTH = "/api/v1/auth/**";
    public static final String API_STUDENT_GET = "/api/v1/student/get";
    public static final String API_STUDENT_GET1 = "/api/v1/student/get1";
    public static final String API_STUDENT_USER_PREFIX = "/api/v1/student/user-api-";
    public static final String API_STUDENT_ADMIN_PREFIX = "/api/v1/student/admin-api-";
    public static final String USER_PROFILE_PATH = "/users/{userId}";
    public static final String USER_BOOKS_PATH = "/users/{userId}/books";
    public static final String USER_BORROWED_BOOKS_PATH = "/users/{userId}/borrowed-books";
    public static final String CREATE_BOOK_PATH = "/books/create";
    public static final String UPDATE_BOOK_PATH = "/books/update/{id}";
    public static final String DELETE_BOOK_PATH = "/books/delete/{id}";
    public static final String GET_ALL_BOOKS_PATH = "/books/getAll";
    public static final String BORROW_BOOK_PATH = "/books/{bookId}/borrow";
    public static final String RETURN_BOOK_PATH = "/books/{bookId}/return";
    public static final String RESERVE_BOOK_PATH = "/books/{bookId}/reserve";
    public static final String CANCEL_RESERVATION_PATH = "/books/{bookId}/cancel-reservation";
    public static final String CREATE_REVIEW_PATH = "/books/{bookId}/reviews/create";
    public static final String GET_BOOK_REVIEWS_PATH = "/books/{bookId}/reviews";
    public static final String UPDATE_REVIEW_PATH = "/books/reviews/{reviewId}/update";
    public static final String DELETE_REVIEW_PATH = "/books/reviews/{reviewId}/delete";


    // Roles
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

}
