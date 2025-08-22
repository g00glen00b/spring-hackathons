package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.WorkoutSchedule;
import codes.dimitri.apps.workouttracker.workout.WorkoutScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteSchedule {
    private final WorkoutScheduleRepository repository;

    public record Parameters(UUID id, UUID userId) {}

    public void execute(Parameters parameters) {
        WorkoutSchedule schedule = repository.findByIdAndUserId(parameters.id(), parameters.userId()).orElseThrow();
        repository.delete(schedule);
    }
}
