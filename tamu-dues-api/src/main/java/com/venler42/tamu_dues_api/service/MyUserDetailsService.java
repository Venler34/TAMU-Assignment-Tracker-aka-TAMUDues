package com.venler42.tamu_dues_api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.venler42.tamu_dues_api.model.CustomUserDetails;
import com.venler42.tamu_dues_api.model.User;
import com.venler42.tamu_dues_api.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public MyUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });

        return new CustomUserDetails(user);
    }
}
