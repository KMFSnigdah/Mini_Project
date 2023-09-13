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
    public static final String SIGN_IN = "/user/login";
    public static final String SIGN_UP = "/user/register";
    public static final String USER_DETAILS = "users/{userId}";
    public static final String USER_OWNED_BOOK = "users/{userId}/books";
    public static final String USER_BORROWED_BOOK = "users/{userId}/borrowed-books";
    public static final String CREATE_BOOK = "books/create";
    public static final String UPDATE_BOOK = "books/update/{id}";
    public static final String DELETE_BOOK = "books/delete/{id}";
    public static final String GET_BOOK = "books/all";
    public static final String BORROW_BOOK = "books/{bookId}/borrow";
    public static final String RETURN_BOOK = "books/{bookId}/return";
    public static final String RESERVE_BOOK = "books/{bookId}/reserve";
    public static final String CANCEL_RESERVATION = "books/{bookId}/cancel";
    public static final String GIVE_REVIEW = "books/{bookId}/reviews/create";
    public static final String UPDATE_REVIEW = "books/reviews/{reviewId}/update";
    public static final String DELETE_REVIEW = "books/reviews/{reviewId}/delete";
    public static final String GET_REVIEW = "books/{bookId}/reviews";
    public static final String GET_USER_HISTORY = "users/{userId}/history";


    // Roles
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

}
