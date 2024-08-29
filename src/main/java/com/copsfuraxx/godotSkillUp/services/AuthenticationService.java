package com.copsfuraxx.godotSkillUp.services;

import com.copsfuraxx.godotSkillUp.exceptions.UsernameAlreadyUsed;
import com.copsfuraxx.godotSkillUp.models.AppUser;
import com.copsfuraxx.godotSkillUp.models.requests.LoginRequest;
import com.copsfuraxx.godotSkillUp.models.requests.RegisterRequest;
import com.copsfuraxx.godotSkillUp.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            AppUser admin = AppUser.builder()
                    .username("admin")
                    .role("admin")
                    .password(passwordEncoder.encode("password"))
                    .build();
            userRepository.save(admin);
        }
    }

    public AppUser register(RegisterRequest request) throws UsernameAlreadyUsed {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyUsed();
        }
        AppUser user = AppUser.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        return userRepository.save(user);
    }

    public AppUser authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        return userRepository.findByUsername(request.username())
                .orElseThrow();
    }
}