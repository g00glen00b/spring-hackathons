package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteWorkout {
    private final WorkoutRepository repository;

    public record Parameters(UUID id, UUID userId) { }

    public void execute(Parameters parameters) {
        Workout workout = repository
            .findByIdAndUserId(parameters.id(), parameters.userId()).orElseThrow();
        repository.delete(workout);
    }
}
