package com.venler42.tamu_dues_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venler42.tamu_dues_api.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}