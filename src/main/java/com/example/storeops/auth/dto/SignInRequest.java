package com.example.storeops.auth.dto;

import lombok.Data;

/**
 * Represents a signin request,
 * containing an email and a password
 */
@Data
public class SignInRequest {

    private String email;

    private String password;

}
