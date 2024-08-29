package com.copsfuraxx.godotSkillUp.repositories;

import com.copsfuraxx.godotSkillUp.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    Optional<Exercise> findById(final long id);
}
