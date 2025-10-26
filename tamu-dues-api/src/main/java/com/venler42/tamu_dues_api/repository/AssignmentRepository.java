package com.venler42.tamu_dues_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venler42.tamu_dues_api.model.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByUserId(Integer userId); // key word findBy is critical so JPA knows its query method (in
                                                   // function
                                                   // UserId looks for userId)

    Optional<Assignment> findByIdAndUserId(Integer assignmentId, Integer userId);
}