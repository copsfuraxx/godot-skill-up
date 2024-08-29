package com.copsfuraxx.godotSkillUp.repositories;

import com.copsfuraxx.godotSkillUp.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(final String username);

    boolean existsByUsername(final String username);
}
