package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.WorkoutSchedule;
import codes.dimitri.apps.workouttracker.workout.WorkoutScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateSchedule {
    private final WorkoutScheduleRepository repository;

    public record Parameters(UUID id, UUID userId, Instant plannedStart, Instant actualStart, Instant actualEnd) { }

    public WorkoutSchedule execute(Parameters parameters) {
        WorkoutSchedule schedule = repository.findByIdAndUserId(parameters.id(), parameters.userId()).orElseThrow();
        schedule.setPlannedStart(parameters.plannedStart());
        schedule.setActualStart(parameters.actualStart());
        schedule.setActualEnd(parameters.actualEnd());
        return schedule;
    }
}
