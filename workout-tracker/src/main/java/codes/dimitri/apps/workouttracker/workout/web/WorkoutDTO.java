package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.Workout;

import java.util.List;
import java.util.UUID;

public record WorkoutDTO(
    UUID id,
    String name,
    List<WorkoutExerciseDTO> exercises
) {
    public static WorkoutDTO of(Workout workout) {
        return new WorkoutDTO(
            workout.getId(),
            workout.getName(),
            workout.getExercises().stream().map(WorkoutExerciseDTO::of).toList()
        );
    }
}
