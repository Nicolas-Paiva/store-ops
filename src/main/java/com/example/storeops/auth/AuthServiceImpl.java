package com.example.storeops.auth;

import com.example.storeops.auth.dto.RegistrationRequest;
import com.example.storeops.auth.dto.RegistrationResponse;
import com.example.storeops.auth.dto.SignInRequest;
import com.example.storeops.auth.dto.SignInResponse;
import com.example.storeops.security.jwt.JwtService;
import com.example.storeops.user.entity.Role;
import com.example.storeops.user.entity.User;
import com.example.storeops.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Register a new user.
     * Verifies the email and password
     * before registering the user
     *
     * @param registrationRequest a registration request
     * containing the user's first name, last name, email
     * and password.
     *
     * @return a registration response with specific
     * messages depending on the request passed as
     * parameter
     */
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest){

        boolean emailAlreadyExists = emailAlreadyExists(registrationRequest);
        boolean validPassword = isPasswordValid(registrationRequest);

        if(emailAlreadyExists){
            return new RegistrationResponse().emailAlreadyExists();
        }

        if(!validPassword){
            return new RegistrationResponse().invalidPassword();
        }

        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new RegistrationResponse().userCreated();
    }

    /**
     * Checks if the email provided by the user
     * is already registered in the database
     *
     * @param registrationRequest the registration
     * request provided by the client
     *
     * @return true if the email already exists
     * false if the email is not in the database
     */
    private boolean emailAlreadyExists(RegistrationRequest registrationRequest){
        Optional<User> user = userRepository.findUserByEmail(registrationRequest.getEmail());

        return user.isPresent();
    }

    /**
     * Verifies whether a password is valid.
     * Checks the length, if it contains special
     * characters and uppercase characters.
     *
     * @param registrationRequest the registration request
     *
     * @return true if the password is valid, false if
     * it isn't
     */
    private boolean isPasswordValid(RegistrationRequest registrationRequest){

        boolean validLength;
        boolean containsSpecialChar;
        boolean containsUppercase;

        String password = registrationRequest.getPassword();

        validLength = password.length() >= 8;

        String specialChars = "!@#$%^&*()-_+=;:'<>,./?{}[]|";
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Set<Character> specialCharsSet = new HashSet<>();
        Set<Character> upperCaseCharsSet = new HashSet<>();

        addCharsToSet(specialCharsSet, specialChars);
        addCharsToSet(upperCaseCharsSet, upperCaseChars);


        containsSpecialChar = stringContainsCharsFromSet(specialCharsSet, password);
        containsUppercase = stringContainsCharsFromSet(upperCaseCharsSet, password);

        return validLength && containsUppercase && containsSpecialChar;
    }

    // Adds a string with special characters
    public void addCharsToSet(Set<Character> set, String specialChars){
        // Adds special chars to the set
        for(int i = 0; i < specialChars.length(); i++){
            set.add(specialChars.charAt(i));
        }
    }

    public boolean stringContainsCharsFromSet(Set<Character> set, String string){
        for(int i = 0; i < string.length(); i++){
            if(set.contains(string.charAt(i)))
                return true;
        }
        return false;
    }


    /**
     * Performs user log in.
     *
     * @param signInRequest a SignRequest, which should contain
     * an email and a password
     *
     * @return a SignInResponse which contains a message and
     * a JWT (if successful), otherwise,an empty token is
     * returned along with an error message
     */
    public SignInResponse signIn(SignInRequest signInRequest){

        /*
         Checks if the user exists and handles the
         exception when the user does not exist
         */
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getEmail(), signInRequest.getPassword()
            ));
        } catch (Exception e){
            return new SignInResponse().unableToLogIn();
        }

        // If the user is authenticated, its details are loaded from the database
        Optional<User> user = userRepository.findUserByEmail(signInRequest.getEmail());

        if(user.isEmpty()){
            return new SignInResponse().unableToLogIn();
        }

        // Generates a JWT based on the userDetails
        var jwt = jwtService.generateToken(user.get());

        return new SignInResponse().loginSuccessfull(jwt);
    }



}
