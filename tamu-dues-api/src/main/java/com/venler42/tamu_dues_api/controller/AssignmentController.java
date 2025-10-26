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

import com.venler42.tamu_dues_api.service.AssignmentService;
import com.venler42.tamu_dues_api.service.UserService;
import com.venler42.tamu_dues_api.model.Assignment;
import com.venler42.tamu_dues_api.model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/users/{userId}/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final UserService userService;

    public AssignmentController(AssignmentService assignmentService, UserService userService) {
        this.assignmentService = assignmentService;
        this.userService = userService;
    }

    @GetMapping
    public List<Assignment> getAllAssignments(@PathVariable Integer userId) {
        return assignmentService.findAllAssignments(userId);
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@PathVariable Integer userId,
            @RequestBody Assignment assignment) {
        Optional<User> user = userService.findUserById(userId);

        if (user.isPresent()) {
            User actualUser = user.get();
            Assignment savedAssignment = assignmentService.createAssignment(actualUser, assignment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
        } else {
            return ResponseEntity.notFound().build(); // couldn't find user
        }
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable Integer userId,
            @PathVariable Integer assignmentId) {
        Optional<Assignment> assignment = assignmentService.findAssignmentByUserAndId(assignmentId, userId);
        if (assignment.isPresent()) {
            return ResponseEntity.ok().body(assignment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Integer userId,
            @PathVariable Integer assignmentId,
            @RequestBody Assignment assignmentRequest) {

        // TODO - work on security of this endpoint in case user doesn't exist stuff
        // like that
        return ResponseEntity.ok().body(assignmentService.updateAssignment(assignmentId, assignmentRequest));
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Integer userId,
            @PathVariable Integer assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.status(204).build();
    }
}
