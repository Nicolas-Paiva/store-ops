package com.example.storeops.security.jwt;

import com.example.storeops.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that is activated
 * for every user request in
 * protected endpoints.
 * The filter verifies the
 * JWT token that's provided
 * by the client
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        try {
            // Verifying if the token starts with Bearer
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            // Retrieves the actual JWT token
            jwt = authHeader.substring(7);

            userEmail = jwtService.extractUsername(jwt);

            // Executes if the email is not empty and the user is not currently authenticated
            if(!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);


                // If the JWT is valid, we set an authentication for the user
                if(jwtService.isTokenValid(jwt, userDetails)){

                    // Creating a security context,
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);
                }

            }

            filterChain.doFilter(request, response);

        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT signature");
        }

    }
}
