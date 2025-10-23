package com.venler42.tamu_dues_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venler42.tamu_dues_api.repository.AssignmentRepository;
import com.venler42.tamu_dues_api.repository.UserRepository;

import com.venler42.tamu_dues_api.model.Assignment;
import com.venler42.tamu_dues_api.model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/users/{userId}/assignments")
public class AssignmentController {
    private final AssignmentRepository assignmentRepo;
    private final UserRepository userRepo;

    public AssignmentController(AssignmentRepository assignmentRepo,
            UserRepository userRepo) {
        this.assignmentRepo = assignmentRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Assignment> getAllAssignments(@PathVariable Long userId) {
        return assignmentRepo.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@PathVariable Long userId,
            @RequestBody Assignment assignment) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isPresent()) {
            User actualUser = user.get();
            assignment.setUser(actualUser);
            Assignment savedAssignment = assignmentRepo.save(assignment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
        } else {
            return ResponseEntity.notFound().build(); // couldn't find user
        }
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable Long userId,
            @PathVariable Long assignmentId) {
        Optional<Assignment> assignment = assignmentRepo.findByIdAndUserId(assignmentId, userId);
        if (assignment.isPresent()) {
            return ResponseEntity.ok().body(assignment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long userId,
            @PathVariable Long assignmentId,
            @RequestBody Assignment assignmentRequest) {
        if (!userRepo.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        return assignmentRepo.findByIdAndUserId(assignmentId, userId)
                .map(assignment -> {
                    assignment.setName(assignmentRequest.getName());
                    assignment.setDescription(assignmentRequest.getDescription());
                    assignment.setDueDate(assignmentRequest.getDueDate());
                    // wouldn't transfer users

                    return ResponseEntity.ok().body(assignmentRepo.save(assignment));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long userId,
            @PathVariable Long assignmentId) {
        return assignmentRepo.findByIdAndUserId(assignmentId, userId)
                .map(assignment -> {
                    assignmentRepo.delete(assignment);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
