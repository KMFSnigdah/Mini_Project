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

    // Roles
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

}
