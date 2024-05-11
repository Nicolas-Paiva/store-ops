package com.example.storeops.controller;

import com.example.storeops.Error.UserNotFoundError;
import com.example.storeops.user.profile.UserProfile;
import com.example.storeops.user.profile.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * Controller responsible for
 * providing methods to access user
 * information, such as the current
 * orders, order status, finished
 * orders and so on
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserProfileService userProfileService;

    /**
     * Method used to retrieve the user's profile data.
     * The method will use the Principal object to retrieve
     * the user's email, which is then used by the profile
     * service, loading the user information
     *
     * @param principal the Principal object obtained
     * after user authentication
     *
     * @return a status code 200 and the UserProfile object
     */
    @GetMapping("/profile")
    public ResponseEntity<?> test(Principal principal){

        String userEmail = principal.getName();

        Optional<UserProfile> userProfile = userProfileService.loadUserProfileData(userEmail);

        if(userProfile.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserNotFoundError());
        }

        return ResponseEntity.ok(userProfile.get());
    }



}
