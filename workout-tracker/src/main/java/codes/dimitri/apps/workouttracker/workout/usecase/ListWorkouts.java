package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import codes.dimitri.apps.workouttracker.workout.WorkoutSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListWorkouts {
    private final WorkoutRepository repository;

    public record Parameters(LocalDate date, ZoneId zoneId, UUID userId, Pageable pageable) { }

    public Page<Workout> execute(Parameters parameters) {
        Specification<Workout> specification = Specification.allOf(
            WorkoutSpecifications.userId(parameters.userId()),
            WorkoutSpecifications.plannedBetween(
                toStartOfDay(parameters.date(), parameters.zoneId()),
                toEndOfDay(parameters.date(), parameters.zoneId())
            )
        );
        return repository.findAll(specification, parameters.pageable());
    }

    private Instant toStartOfDay(LocalDate date, ZoneId zoneId) {
        if (date == null || zoneId == null) return null;
        return date.atStartOfDay(zoneId).toInstant();
    }

    private Instant toEndOfDay(LocalDate date, ZoneId zoneId) {
        if (date == null || zoneId == null) return null;
        return date.plusDays(1).atStartOfDay(zoneId).toInstant().minusMillis(1);
    }
}
