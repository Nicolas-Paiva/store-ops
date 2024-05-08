package com.example.storeops.user.service;

import com.example.storeops.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Actual implementation of the userService,
 * utilising the UserRepository in order
 * to load users from out database
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;



//    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService(){
//        return new org.springframework.security.core.userdetails.UserDetailsService(){
//
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return userRepository.findUserByEmail(username)
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            }
//
//        };
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
// return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));