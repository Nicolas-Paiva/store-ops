package com.example.storeops.user.profile;

import java.util.Optional;

public interface UserProfileService {

    // Loads the user's profile based on the user's email
    Optional<UserProfile> loadUserProfileData(String userEmail);

}
