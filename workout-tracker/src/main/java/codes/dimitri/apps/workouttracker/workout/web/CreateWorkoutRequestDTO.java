package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.usecase.CreateWorkout;

import java.util.List;
import java.util.UUID;

public record CreateWorkoutRequestDTO(String name, List<CreateWorkoutExerciseRequestDTO> exercises) {

    private record CreateWorkoutExerciseRequestDTO(UUID exerciseId, int sets, int reps, Double weightInKg) {
        public CreateWorkout.Parameters.ExerciseParameters toParameters() {
            return new CreateWorkout.Parameters.ExerciseParameters(
                exerciseId,
                reps,
                sets,
                weightInKg
            );
        }
    }

    public CreateWorkout.Parameters toParameters(UUID userId) {
        return new CreateWorkout.Parameters(
            name,
            userId,
            exercises.stream().map(CreateWorkoutExerciseRequestDTO::toParameters).toList()
        );
    }
}
