package com.venler42.tamu_dues_api.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.venler42.tamu_dues_api.model.User;
import com.venler42.tamu_dues_api.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* User Endpoints */
    @GetMapping
    public ResponseEntity<User> getUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(Authentication authentication, @RequestBody User userDetails) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        // Cannot update username cause it will mess with JWT Token and verification
        // Password encoded in userService if password is updated

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(userService.updateUser(user.get().getId(), userDetails));
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(user.get().getId());
        return ResponseEntity.status(204).build();
    }

}
