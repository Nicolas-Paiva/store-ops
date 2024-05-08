package com.example.storeops.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Represents the registration request
 * of a user, which must meet certain
 * criteria.
 */
@Data
public class RegistrationRequest {

    @NotBlank
    @Size(min = 3, message = "Name should have at least 3 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, message = "Last name should have at least 3 characters")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
