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

    public RegistrationResponse userCreated(){
        created = true;
        message = "User created";
        return this;
    }

    public RegistrationResponse emailAlreadyExists(){
        created = false;
        message = "Email already exists";
        return this;
    }

    public RegistrationResponse invalidPassword(){
        created = false;
        message = "Password must have at least 8 characters," +
                " one special character and one uppercase letter";
        return this;
    }
}
