package com.example.storeops.controller;

import com.example.storeops.auth.service.AuthenticationService;
import com.example.storeops.auth.dto.RegistrationRequest;
import com.example.storeops.auth.dto.RegistrationResponse;
import com.example.storeops.auth.dto.SignInRequest;
import com.example.storeops.auth.dto.SignInResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller dedicated to providing
 * endpoints for registration and login
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Endpoint for registering a user.
     *
     * @param registrationRequest a registration request.
     *
     * @return if the request is valid, the user is saved and a
     * registration response is returned, indicating that
     * the user has been created along with a 201 status code
     *
     * If the request is not valid, an object with all
     * the errors is returned along with a 400 status code
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody RegistrationRequest registrationRequest){

        RegistrationResponse response = authenticationService.registerUser(registrationRequest);

        if(!response.isCreated()){
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Enpoint for signing a user in.
     *
     * @param signInRequest A signin request, which should
     * contain an email and a password.
     *
     * @return if the request is valid, a SignInResponse object
     * containing the JWT is sent back to the client
     *
     * If the request is not valid, a SignIngResponse object
     * is sent back with the message "Invalid username or password"
     * containing an empty JWT
     *
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){

        SignInResponse response = authenticationService.signIn(signInRequest);

        if(response.getToken() == null){
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

}
