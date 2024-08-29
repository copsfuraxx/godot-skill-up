package com.copsfuraxx.godotSkillUp.models.requests;

public record UpdateExerciseRequest(
        long id,
        String name,
        String description,
        String baseCodeUrl
) {}
