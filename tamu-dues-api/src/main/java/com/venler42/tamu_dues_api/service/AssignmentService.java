package com.venler42.tamu_dues_api.service;

import org.springframework.stereotype.Service;

import com.venler42.tamu_dues_api.model.Assignment;
import com.venler42.tamu_dues_api.model.User;
import com.venler42.tamu_dues_api.repository.AssignmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepo;

    public AssignmentService(AssignmentRepository assignmentRepo) {
        this.assignmentRepo = assignmentRepo;
    }

    public Assignment createAssignment(User user, Assignment assignment) {
        assignment.setUser(user);
        return assignmentRepo.save(assignment);
    }

    public Optional<Assignment> findAssignmentByUserAndId(Integer assignmentId, Integer userId) {
        return assignmentRepo.findByIdAndUserId(assignmentId, userId);
    }

    public List<Assignment> findAllAssignments(Integer userId) {
        return assignmentRepo.findByUserId(userId);
    }

    public Assignment updateAssignment(Integer assignmentId, Assignment updatedAssignment) {
        return assignmentRepo.findById(assignmentId)
                .map(existingAssignment -> {
                    if (updatedAssignment.getName() != null)
                        existingAssignment.setName(updatedAssignment.getName());
                    if (updatedAssignment.getDescription() != null)
                        existingAssignment.setDescription(updatedAssignment.getDescription());
                    if (updatedAssignment.getDueDate() != null)
                        existingAssignment.setDueDate(updatedAssignment.getDueDate());
                    if (updatedAssignment.getStatus() != null)
                        existingAssignment.setStatus(updatedAssignment.getStatus());
                    if (updatedAssignment.getPriority() != null)
                        existingAssignment.setPriority(updatedAssignment.getPriority());

                    return assignmentRepo.save(existingAssignment);
                })
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));
    }

    public void deleteAssignment(Integer assignmentId) {
        if (!assignmentRepo.existsById(assignmentId)) {
            throw new RuntimeException("User not found with ID: " + assignmentId);
        }
        assignmentRepo.deleteById(assignmentId);
    }
}