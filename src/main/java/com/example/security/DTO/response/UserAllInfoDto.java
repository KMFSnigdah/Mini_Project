package com.example.security.DTO.response;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAllInfoDto {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Role role;
    public UserAllInfoDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.role = user.getRole();
    }
}
