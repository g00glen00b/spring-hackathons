package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.exercise.Exercise;
import codes.dimitri.apps.workouttracker.exercise.ExerciseRepository;
import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutExercise;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CreateWorkout {
    private final WorkoutRepository repository;
    private final ExerciseRepository exerciseRepository;

    public record Parameters(String name, UUID userId, List<ExerciseParameters> exercises) {
        public record ExerciseParameters(UUID exerciseId, int reps, int sets, Double weightInKg) {
        }
    }

    public Workout execute(Parameters parameters) {
        Workout workout = repository.save(new Workout(parameters.name(), parameters.userId()));
        addExercisesToWorkout(workout, parameters);
        return workout;
    }

    private void addExercisesToWorkout(Workout workout, Parameters parameters) {
        parameters
            .exercises()
            .forEach(exercise -> addExerciseToWorkout(workout, exercise));
    }

    private void addExerciseToWorkout(Workout workout, Parameters.ExerciseParameters parameters) {
        Exercise exercise = exerciseRepository.findById(parameters.exerciseId()).orElseThrow();
        workout.addExercise(new WorkoutExercise(
            exercise,
            parameters.reps(),
            parameters.sets(),
            parameters.weightInKg()
        ));
    }
}
