package com.venler42.tamu_dues_api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.venler42.tamu_dues_api.model.LoginRequest;
import com.venler42.tamu_dues_api.model.TokenResponse;
import com.venler42.tamu_dues_api.model.User;
import com.venler42.tamu_dues_api.repository.UserRepository;
import com.venler42.tamu_dues_api.util.JWTUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getId().toString()); // or username
        return new TokenResponse(token);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepo.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(Integer id, User updatedUser) {
        return userRepo.findById(id)
                .map(existingUser -> {
                    if (updatedUser.getUsername() != null)
                        existingUser.setUsername(updatedUser.getUsername());
                    if (updatedUser.getPassword() != null &&
                            !updatedUser.getPassword().isBlank()) {
                        // existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return userRepo.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public void deleteUser(Integer id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepo.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
