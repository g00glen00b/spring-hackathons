package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.WorkoutReport;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import codes.dimitri.apps.workouttracker.workout.WorkoutScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class GetWorkoutReport {
    private final WorkoutRepository repository;
    private final WorkoutScheduleRepository scheduleRepository;

    public record Parameters(UUID workoutId, UUID userId) {}

    public WorkoutReport execute(Parameters parameters) {
        var workoutId = parameters.workoutId();
        var userId = parameters.userId();
        if (!repository.existsByIdAndUserId(workoutId, userId)) {
            throw new IllegalArgumentException("Workout not found");
        }
        int totalTimesCompleted = scheduleRepository.countAllByWorkoutIdAndActualEndIsNotNull(workoutId);
        Duration totalTimeSpent = Duration.ofSeconds(scheduleRepository.totalActualDurationByWorkoutId(workoutId));
        return new WorkoutReport(
            totalTimesCompleted,
            totalTimeSpent.dividedBy(totalTimesCompleted),
            Duration.ofSeconds(scheduleRepository.minActualDurationByWorkoutId(workoutId)),
            Duration.ofSeconds(scheduleRepository.maxActualDurationByWorkoutId(workoutId)),
            totalTimeSpent
        );
    }
}
