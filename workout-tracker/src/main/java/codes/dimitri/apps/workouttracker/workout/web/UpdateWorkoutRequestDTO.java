package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.usecase.UpdateWorkout;

import java.util.List;
import java.util.UUID;

public record UpdateWorkoutRequestDTO(String name, List<UpdateWorkoutExerciseRequestDTO> exercises) {

    private record UpdateWorkoutExerciseRequestDTO(UUID id, UUID exerciseId, int sets, int reps, Double weightInKg) {
        public UpdateWorkout.Parameters.ExerciseParameters toParameters() {
            return new UpdateWorkout.Parameters.ExerciseParameters(
                id,
                exerciseId,
                reps,
                sets,
                weightInKg
            );
        }
    }

    public UpdateWorkout.Parameters toParameters(UUID workoutId, UUID userId) {
        return new UpdateWorkout.Parameters(
            workoutId,
            userId,
            name,
            exercises.stream().map(UpdateWorkoutExerciseRequestDTO::toParameters).toList()
        );
    }
}
