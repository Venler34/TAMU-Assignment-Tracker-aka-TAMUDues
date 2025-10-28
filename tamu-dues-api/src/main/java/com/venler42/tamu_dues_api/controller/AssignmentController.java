package com.venler42.tamu_dues_api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import com.venler42.tamu_dues_api.service.AssignmentService;
import com.venler42.tamu_dues_api.service.UserService;
import com.venler42.tamu_dues_api.model.Assignment;
import com.venler42.tamu_dues_api.model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/users/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final UserService userService;

    public AssignmentController(AssignmentService assignmentService, UserService userService) {
        this.assignmentService = assignmentService;
        this.userService = userService;
    }

    @GetMapping
    public List<Assignment> getAllAssignments(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (!user.isPresent()) {
            return new ArrayList<>();
        }

        return assignmentService.findAllAssignments(user.get().getId());
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(Authentication authentication,
            @RequestBody Assignment assignment) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            User actualUser = user.get();
            Assignment savedAssignment = assignmentService.createAssignment(actualUser, assignment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
        } else {
            return ResponseEntity.notFound().build(); // couldn't find user
        }
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignment(Authentication authentication, @PathVariable Integer assignmentId) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Assignment> assignment = assignmentService.findAssignmentByUserAndId(assignmentId, user.get().getId());
        if (assignment.isPresent()) {
            return ResponseEntity.ok().body(assignment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(Authentication authentication,
            @PathVariable Integer assignmentId,
            @RequestBody Assignment assignmentRequest) {

        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Assignment> assignment = assignmentService.findAssignmentByUserAndId(assignmentId, user.get().getId());
        if (!assignment.isPresent()) {
            return ResponseEntity.notFound().build(); // ensures user has this assignment
        }

        return ResponseEntity.ok().body(assignmentService.updateAssignment(assignmentId, assignmentRequest));
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(Authentication authentication,
            @PathVariable Integer assignmentId) {

        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Assignment> assignment = assignmentService.findAssignmentByUserAndId(assignmentId, user.get().getId());
        if (!assignment.isPresent()) {
            return ResponseEntity.notFound().build(); // ensures user has this assignment
        }

        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.status(204).build();
    }
}
