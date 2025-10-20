package com.venler42.tamu_dues_api.controller;

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

@RestController
@RequestMapping("/v1")
public class RestAPI {
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
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        return ResponseEntity.status(200).body(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        return ResponseEntity.accepted().build();
    }

}
