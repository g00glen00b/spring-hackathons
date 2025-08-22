package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.exercise.web.ExerciseDTO;
import codes.dimitri.apps.workouttracker.workout.WorkoutExercise;

import java.util.UUID;

public record WorkoutExerciseDTO(
    UUID id,
    ExerciseDTO exercise,
    int reps,
    int sets,
    Double weightInKg
) {
    public static WorkoutExerciseDTO of(WorkoutExercise exercise) {
        return new WorkoutExerciseDTO(
            exercise.getId(),
            ExerciseDTO.of(exercise.getExercise()),
            exercise.getReps(),
            exercise.getSets(),
            exercise.getWeightInKg()
        );
    }
}
