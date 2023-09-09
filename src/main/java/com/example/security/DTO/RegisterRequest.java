package com.example.security.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty
    @Size(min = 2, message = "First name should have at least 2 character")
    private String firstname;

    @NotEmpty
    @Size(min = 2, message = "Last name should have at least 2 character")
    private String lastname;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be null or empty")
    private String password;

    @NotEmpty(message = "Role should not be null or empty")
    private String role;


}
