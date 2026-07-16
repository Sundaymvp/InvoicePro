package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.dto.request.LoginRequest;
import com.sundaymvp.invoice_api.dto.response.AuthResponse;
import com.sundaymvp.invoice_api.dto.response.UserResponse;
import com.sundaymvp.invoice_api.entity.User;
import com.sundaymvp.invoice_api.mapper.UserMapper;
import com.sundaymvp.invoice_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            UserService userService) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(userDetails);

        User user = userService.findByEmail(request.getEmail());

        UserResponse response = UserMapper.toResponse(user);

        return new AuthResponse(token, response);
    }
}