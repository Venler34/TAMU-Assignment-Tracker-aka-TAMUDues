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

import com.venler42.tamu_dues_api.model.Assignment;
import com.venler42.tamu_dues_api.model.User;

import com.venler42.tamu_dues_api.repository.UserRepository;
import com.venler42.tamu_dues_api.repository.AssignmentRepository;
import com.venler42.tamu_dues_api.repository.SectionRepository;

@RestController
@RequestMapping("/v1")
public class RestAPI {
    private final UserRepository userRepo;

    public RestAPI(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /* Assignment Endpoints */
    @GetMapping("/assignment/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable int id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/assignment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Assignment> addAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.status(201).body(assignment);
    }

    @PutMapping("/assignment/{id}") // best practice not to include id in body (will store on client side)
    public ResponseEntity<Assignment> updateAssignment(@PathVariable int id, @RequestBody Assignment assignment) {
        return ResponseEntity.status(200).body(assignment);
    }

    @DeleteMapping("/assignment/{id}")
    public ResponseEntity<Assignment> deleteAssignment(@PathVariable int id) {
        return ResponseEntity.accepted().build();
    }

    /* User Endpoints */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        Optional<User> user = userRepo.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userRepo.save(user));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails) {
        // returns optional (may contain or not) (let's you transform value within if
        // exists without checking if present, wraps in new optional or remains empty
        // optional)
        return userRepo.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());

                    User updatedUser = userRepo.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // generates fallback value or retrieves value
                                                                     // inside optional (supplier function is called)
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        if (userRepo.existsById(id)) { // SELECT 1 FROM table where id = ?
            userRepo.deleteById(id); // may throw exception if id doesn't exist
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
