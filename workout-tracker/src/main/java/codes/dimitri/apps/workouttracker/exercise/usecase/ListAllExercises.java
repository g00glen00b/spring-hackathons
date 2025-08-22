package codes.dimitri.apps.workouttracker.exercise.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.exercise.Exercise;
import codes.dimitri.apps.workouttracker.exercise.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListAllExercises {
    private final ExerciseRepository repository;

    public record Parameters(Pageable pageable) {
    }

    public Page<Exercise> execute(Parameters parameters) {
        return repository.findAll(parameters.pageable());
    }
}
