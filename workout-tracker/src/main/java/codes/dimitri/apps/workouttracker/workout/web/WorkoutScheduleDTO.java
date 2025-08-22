package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.WorkoutSchedule;

import java.time.Instant;
import java.util.UUID;

public record WorkoutScheduleDTO(UUID id, Instant plannedStart, Instant actualStart, Instant actualEnd) {
    public static WorkoutScheduleDTO of(WorkoutSchedule schedule) {
        return new WorkoutScheduleDTO(
            schedule.getId(),
            schedule.getPlannedStart(),
            schedule.getActualStart(),
            schedule.getActualEnd()
        );
    }
}
