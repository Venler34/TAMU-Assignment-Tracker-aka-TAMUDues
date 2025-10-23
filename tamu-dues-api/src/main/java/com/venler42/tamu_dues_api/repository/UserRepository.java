package com.venler42.tamu_dues_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venler42.tamu_dues_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
