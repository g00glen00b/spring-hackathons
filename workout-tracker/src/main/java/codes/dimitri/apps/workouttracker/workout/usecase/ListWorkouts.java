package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListWorkouts {
    private final WorkoutRepository repository;

    public record Parameters(LocalDate date, ZoneId zoneId, UUID userId, Pageable pageable) {}

    public Page<Workout> execute(Parameters parameters) {
        return repository.findAllByUserId(parameters.userId(), parameters.pageable());
    }
}
