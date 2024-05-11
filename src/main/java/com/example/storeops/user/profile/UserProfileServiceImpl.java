package com.example.storeops.user.profile;

import com.example.storeops.user.entity.User;
import com.example.storeops.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService{

    private final UserRepository userRepository;

    /**
     * Used to return a UserProfileData
     * object to the client taking as parameter
     * the user's email. This method is used
     * by the user profile endpoint, receiving
     * the user's email from the Principal object
     *
     * @param userEmail the user's email that should
     * be retrieved from the Principal object
     *
     * @return a UserProfileData object if the user can
     * be found, otherwise returns an empty optional
     */
    @Override
    public Optional<UserProfile> loadUserProfileData(String userEmail){
        Optional<User> user = userRepository.findUserByEmail(userEmail);

        if(user.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(UserProfile.userToDto(user.get()));
    }

}
