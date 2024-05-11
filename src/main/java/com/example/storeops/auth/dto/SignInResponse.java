package com.example.storeops.auth.dto;

import lombok.Data;

/**
 * Represents the sign in response
 * sent back to the client after
 * a login attempt.
 *
 */
@Data
public class SignInResponse {

    private String message;

    private String token;

    // Sent when the login is successful
    public SignInResponse loginSuccessfull(String token){
        message = "Log in successful";
        this.token = token;
        return this;
    }

    // Sent when the user does not exist or when the provided data is not valid
    public SignInResponse unableToLogIn(){
        message = "Wrong email or password";
        this.token = null;
        return this;
    }

}
