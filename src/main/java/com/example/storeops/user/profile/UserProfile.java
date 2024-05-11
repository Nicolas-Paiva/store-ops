package com.example.storeops.user.profile;

import com.example.storeops.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private String firstName;

    private String lastName;

    private String email;

    public static UserProfile userToDto(User user){
        return UserProfile.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail()).build();
    }

}
