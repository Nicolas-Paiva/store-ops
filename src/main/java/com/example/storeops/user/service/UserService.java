package com.example.storeops.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Provides the method "loadUserByUsername"
 * method, responsible for loading a user
 * from our database in the format of
 * the UserDetails required by Spring Security
 */
public interface UserService {

    public UserDetailsService userDetailsService();

}
