package com.venler42.tamu_dues_api.controller;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venler42.tamu_dues_api.model.User;

import com.venler42.tamu_dues_api.repository.UserRepository;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /* User Endpoints */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable long userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userRepo.save(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody User userDetails) {
        // returns optional (may contain or not) (let's you transform value within if
        // exists without checking if present, wraps in new optional or remains empty
        // optional)
        return userRepo.findById(userId)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());

                    User updatedUser = userRepo.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // generates fallback value or retrieves value
                                                                     // inside optional (supplier function is called)
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable long userId) {
        if (userRepo.existsById(userId)) { // SELECT 1 FROM table where id = ?
            userRepo.deleteById(userId); // may throw exception if id doesn't exist
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
