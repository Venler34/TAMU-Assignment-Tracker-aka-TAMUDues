package com.venler42.tamu_dues_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venler42.tamu_dues_api.model.LoginRequest;
import com.venler42.tamu_dues_api.model.TokenResponse;
import com.venler42.tamu_dues_api.model.User;
import com.venler42.tamu_dues_api.service.UserService;
import com.venler42.tamu_dues_api.util.JWTUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private UserService userService;
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody LoginRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        User result = userService.createUser(user);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        // TokenResponse response = userService.login(loginRequest);

        // return ResponseEntity.ok(response);
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            return ResponseEntity.notFound().build();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        TokenResponse response = new TokenResponse(jwtToken);

        return ResponseEntity.ok(response);
    }

}
