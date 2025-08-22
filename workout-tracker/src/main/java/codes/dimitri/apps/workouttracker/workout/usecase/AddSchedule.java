package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import codes.dimitri.apps.workouttracker.workout.WorkoutSchedule;
import codes.dimitri.apps.workouttracker.workout.WorkoutScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class AddSchedule {
    private final WorkoutRepository workoutRepository;
    private final WorkoutScheduleRepository repository;

    public record Parameters(UUID workoutId, UUID scheduleId, Instant plannedStart) { }

    public WorkoutSchedule execute(Parameters parameters) {
        Workout workout = workoutRepository
            .findByIdAndUserId(parameters.workoutId(), parameters.scheduleId())
            .orElseThrow();
        return repository.save(new WorkoutSchedule(workout, parameters.plannedStart));
    }
}
