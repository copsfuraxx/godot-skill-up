package com.copsfuraxx.godotSkillUp.controllers;

import com.copsfuraxx.godotSkillUp.exceptions.BadRequest;
import com.copsfuraxx.godotSkillUp.exceptions.ResourceNotFound;
import com.copsfuraxx.godotSkillUp.models.Exercise;
import com.copsfuraxx.godotSkillUp.models.requests.CreateExerciseRequest;
import com.copsfuraxx.godotSkillUp.models.requests.UpdateExerciseRequest;
import com.copsfuraxx.godotSkillUp.models.responses.ExerciseResponse;
import com.copsfuraxx.godotSkillUp.models.responses.MessageResponse;
import com.copsfuraxx.godotSkillUp.models.responses.ResumeExerciseResponse;
import com.copsfuraxx.godotSkillUp.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/exercises")
@RestController
public class ExerciseController {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody CreateExerciseRequest request) throws IOException {
        Exercise exercise = Exercise.builder()
                .name(request.name())
                .description(request.description())
                .baseCodeUrl(request.baseCodeUrl())
                .build();
        exerciseRepository.save(exercise);
        return ResponseEntity.ok(new MessageResponse("Exercise créé"));
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping
    public ResponseEntity<MessageResponse> update(@RequestBody UpdateExerciseRequest request) throws IOException {
        Exercise exercise = exerciseRepository.findById(request.id()).orElseThrow(() -> new BadRequest("L'éxercise id n'est pas valide"));
        exercise.setName(request.name());
        exercise.setDescription(request.description());
        exercise.setBaseCodeUrl(request.baseCodeUrl());
        exerciseRepository.save(exercise);
        return ResponseEntity.ok(new MessageResponse("Exercise créé"));
    }

    @GetMapping()
    public ResponseEntity<ResumeExerciseResponse[]> all() {
        List<Exercise> exercises = exerciseRepository.findAll();
        ResumeExerciseResponse[] res = new ResumeExerciseResponse[exercises.size()];
        for (int i = 0; i < res.length; i++) {
            Exercise exercise = exercises.get(i);
            res[i] = new ResumeExerciseResponse(exercise.getId(), exercise.getName());
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponse> read(@PathVariable long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(ResourceNotFound::new);
        return ResponseEntity.ok(new ExerciseResponse(exercise.getName(), exercise.getDescription(), exercise.getBaseCodeUrl()));
    }
}
