package com.example.storeops.auth;

import com.example.storeops.auth.dto.RegistrationRequest;
import com.example.storeops.auth.dto.RegistrationResponse;
import com.example.storeops.auth.dto.SignInRequest;
import com.example.storeops.auth.dto.SignInResponse;

/**
 * Provides methods related to authentication,
 * such as registering and logging a user.
 */
public interface AuthenticationService {

    RegistrationResponse registerUser(RegistrationRequest registrationRequest);

    SignInResponse signIn(SignInRequest signInRequest);

}
