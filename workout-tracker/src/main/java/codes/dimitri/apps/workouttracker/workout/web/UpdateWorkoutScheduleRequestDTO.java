package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.usecase.UpdateSchedule;

import java.time.Instant;
import java.util.UUID;

public record UpdateWorkoutScheduleRequestDTO(Instant plannedStart, Instant actualStart, Instant actualEnd) {
    public UpdateSchedule.Parameters toParameters(UUID id, UUID userId) {
        return new UpdateSchedule.Parameters(id, userId, plannedStart, actualStart, actualEnd);
    }
}
