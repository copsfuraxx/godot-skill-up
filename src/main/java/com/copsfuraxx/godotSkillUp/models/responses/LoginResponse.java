package com.copsfuraxx.godotSkillUp.models.responses;

public record LoginResponse(String token, long expirationTime, String role, String username) {
}
