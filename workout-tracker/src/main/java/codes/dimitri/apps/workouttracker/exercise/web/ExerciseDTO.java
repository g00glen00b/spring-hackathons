package codes.dimitri.apps.workouttracker.exercise.web;

import codes.dimitri.apps.workouttracker.exercise.Exercise;
import codes.dimitri.apps.workouttracker.exercise.ExerciseCategory;
import codes.dimitri.apps.workouttracker.exercise.MuscleGroup;

import java.util.UUID;

public record ExerciseDTO(
    UUID id,
    String name,
    String description,
    ExerciseCategory category,
    MuscleGroup muscleGroup
) {
    public static ExerciseDTO of(Exercise exercise) {
        return new ExerciseDTO(
            exercise.getId(),
            exercise.getName(),
            exercise.getDescription(),
            exercise.getCategory(),
            exercise.getMuscleGroup()
        );
    }
}
