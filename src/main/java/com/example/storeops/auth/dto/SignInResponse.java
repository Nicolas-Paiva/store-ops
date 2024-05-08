package com.example.storeops.auth.dto;

import lombok.Data;

/**
 * Represents the sign in response
 * sent back to the client after
 * a log in attempt.
 *
 */
@Data
public class SignInResponse {

    private String message;

    private String token;

    public SignInResponse loginSuccessfull(String token){
        message = "Log in successful";
        this.token = token;
        return this;
    }

    public SignInResponse unableToLogIn(){
        message = "Wrong email or password";
        this.token = null;
        return this;
    }

}
