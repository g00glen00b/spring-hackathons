package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.usecase.AddSchedule;

import java.time.Instant;
import java.util.UUID;

public record AddWorkoutScheduleRequestDTO(Instant plannedStart) {
    public AddSchedule.Parameters toParameters(UUID workoutId, UUID userId) {
        return new AddSchedule.Parameters(workoutId, userId, plannedStart);
    }
}
