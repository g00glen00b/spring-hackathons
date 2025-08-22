package codes.dimitri.apps.workouttracker.workout;

import java.time.Duration;

public record WorkoutReport(
    int totalTimesCompleted,
    Duration averageDuration,
    Duration shortestDuration,
    Duration longestDuration,
    Duration totalTimeSpent
) {
}
