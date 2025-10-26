package com.venler42.tamu_dues_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venler42.tamu_dues_api.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
