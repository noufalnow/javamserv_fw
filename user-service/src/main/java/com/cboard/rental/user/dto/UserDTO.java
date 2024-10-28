package com.cboard.rental.user.dto;

import com.cboard.rental.user.validation.OnCreate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @NotBlank(message = "Email is mandatory", groups = OnCreate.class) // Only required for POST (OnCreate)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory", groups = OnCreate.class) // Only required for POST (OnCreate)
    private String password;

    private String[] roles; // List of roles assigned to the user
}
