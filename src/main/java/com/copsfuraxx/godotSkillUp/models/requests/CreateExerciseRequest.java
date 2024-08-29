package com.copsfuraxx.godotSkillUp.models.requests;

public record CreateExerciseRequest(
        String name,
        String description,
        String baseCodeUrl
) {}
