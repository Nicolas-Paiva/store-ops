package com.example.storeops.auth.dto;

import lombok.*;

/**
 * Represents the response that
 * is sent to the front-end regarding
 * the user registration process
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    boolean created;
    String message;

    // Sent when the registration is a success
    public RegistrationResponse userCreated(){
        created = true;
        message = "User created";
        return this;
    }

    // Sent when the user's provided email is already registered
    public RegistrationResponse emailAlreadyExists(){
        created = false;
        message = "Email already exists";
        return this;
    }

    // Sent when the password provided by the user does not match the criteria
    public RegistrationResponse invalidPassword(){
        created = false;
        message = "Password must have at least 8 characters," +
                " one special character and one uppercase letter";
        return this;
    }
}
