package com.copsfuraxx.godotSkillUp.controllers;

import com.copsfuraxx.godotSkillUp.models.AppUser;
import com.copsfuraxx.godotSkillUp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<AppUser> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(currentUser);
    }
}
