package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.WorkoutReport;

import java.time.Duration;

public record WorkoutReportDTO(
    int totalTimesCompleted,
    Duration averageDuration,
    Duration shortestDuration,
    Duration longestDuration,
    Duration totalTimeSpent
) {
    public static WorkoutReportDTO from(WorkoutReport report) {
        return new WorkoutReportDTO(
            report.totalTimesCompleted(),
            report.averageDuration(),
            report.shortestDuration(),
            report.longestDuration(),
            report.totalTimeSpent()
        );
    }
}
