// package com.venler42.tamu_dues_api.controller;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.venler42.tamu_dues_api.model.User;
// import com.venler42.tamu_dues_api.repository.UserRepository;

// @RequestMapping("/auth")
// public class AuthController {
// private final UserRepository userRepo;
// private final PasswordEncoder passwordEncoder;

// public AuthController(UserRepository userRepo, PasswordEncoder
// passwordEncoder) {
// this.userRepo = userRepo;
// this.passwordEncoder = passwordEncoder;
// }

// @PostMapping("/register")
// public String registerUser(@RequestParam String username, @RequestParam
// String password) {
// User user = new User();
// user.setUsername(username);
// user.setPassword(passwordEncoder.encode(password));
// userRepo.save(user);

// return "redirect:/login";
// }
// }
