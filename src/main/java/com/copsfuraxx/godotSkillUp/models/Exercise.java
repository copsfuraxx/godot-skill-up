package com.copsfuraxx.godotSkillUp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String baseCodeUrl;
}
