package com.example.security.Controller;

import com.example.security.exception.AuthenticationException;
import com.example.security.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private final UserRepository userRepository;

    public StudentController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This api authenticates for USER ROLE
    // http://localhost:8080/api/v1/student/get
    @GetMapping("/get")
    public ResponseEntity<String> sayHello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException(HttpStatus.NOT_FOUND, "User not found"));
        System.out.println(user.getFirstName() + "hello mallika ");
        return ResponseEntity.ok("Hello from User API 1");
    }

    // This api authenticates for ADMIN ROLE
    // http://localhost:8080/api/v1/student/get1
    @GetMapping("/get1")
    public ResponseEntity<String> sayHello1(){
        return ResponseEntity.ok("Hello from Admin API 1");
    }

    // This API authenticate for ROLE USER
    // http://localhost:8080/api/v1/student/user-api-1
    @GetMapping("/user-api-1")
    public ResponseEntity<String> userApi1(){
        return ResponseEntity.ok("Hello from User API 1");
    }

    // This API authenticate for ROLE USER
    // http://localhost:8080/api/v1/student/user-api-2
    @GetMapping("/user-api-2")
    public ResponseEntity<String> userApi2(){
        return ResponseEntity.ok("Hello from User API 2");
    }

    // This API authenticate for ROLE USER
    // http://localhost:8080/api/v1/student/user-api-3
    @GetMapping("/user-api-3")
    public ResponseEntity<String> userApi3(){
        return ResponseEntity.ok("Hello from User API 3");
    }

    // This API authenticate for ROLE USER
    // http://localhost:8080/api/v1/student/user-api-4
    @GetMapping("/user-api-4")
    public ResponseEntity<String> userApi4(){
        return ResponseEntity.ok("Hello from User API 4");
    }

    // This API authenticate for ROLE ADMIN
    // http://localhost:8080/api/v1/student/admin-api-1
    @GetMapping("/admin-api-1")
    public ResponseEntity<String> adminApi1(){
        return ResponseEntity.ok("Hello from Admin API 1");
    }

    // This API authenticate for ROLE ADMIN
    // http://localhost:8080/api/v1/student/admin-api-2
    @GetMapping("/admin-api-2")
    public ResponseEntity<String> adminApi2(){
        return ResponseEntity.ok("Hello from Admin API 2");
    }

    // This API authenticate for ROLE ADMIN
    // http://localhost:8080/api/v1/student/admin-api-3
    @GetMapping("/admin-api-3")
    public ResponseEntity<String> adminApi3(){
        return ResponseEntity.ok("Hello from Admin API 3");
    }

    // This API authenticate for ROLE ADMIN
    // http://localhost:8080/api/v1/student/admin-api-4
    @GetMapping("/admin-api-4")
    public ResponseEntity<String> adminApi4(){
        return ResponseEntity.ok("Hello from Admin API 4");
    }
}
