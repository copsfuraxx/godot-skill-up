package com.copsfuraxx.godotSkillUp.controllers;

import com.copsfuraxx.godotSkillUp.exceptions.UsernameAlreadyUsed;
import com.copsfuraxx.godotSkillUp.models.AppUser;
import com.copsfuraxx.godotSkillUp.models.requests.LoginRequest;
import com.copsfuraxx.godotSkillUp.models.requests.RegisterRequest;
import com.copsfuraxx.godotSkillUp.models.responses.LoginResponse;
import com.copsfuraxx.godotSkillUp.services.AuthenticationService;
import com.copsfuraxx.godotSkillUp.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody RegisterRequest request) throws UsernameAlreadyUsed {
        AppUser registeredUser = authenticationService.register(request);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        AppUser authenticatedUser = authenticationService.authenticate(request);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(
                jwtToken,
                jwtService.getExpirationTime(),
                authenticatedUser.getRole(),
                authenticatedUser.getUsername());

        return ResponseEntity.ok(loginResponse);
    }
}