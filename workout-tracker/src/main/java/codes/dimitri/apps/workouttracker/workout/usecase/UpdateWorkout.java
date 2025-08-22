package codes.dimitri.apps.workouttracker.workout.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.exercise.Exercise;
import codes.dimitri.apps.workouttracker.exercise.ExerciseRepository;
import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutExercise;
import codes.dimitri.apps.workouttracker.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateWorkout {
    private final WorkoutRepository repository;
    private final ExerciseRepository exerciseRepository;

    public record Parameters(UUID id, UUID userId, String name, List<ExerciseParameters> exercises) {
        public record ExerciseParameters(UUID id, UUID exerciseId, int reps, int sets, Double weightInKg) {
        }
    }

    public Workout execute(Parameters parameters) {
        Workout workout = repository.findByIdAndUserId(parameters.id(), parameters.userId()).orElseThrow();
        workout.setName(parameters.name());
        updateExercises(workout, parameters);
        return workout;
    }

    private void updateExercises(Workout workout, Parameters parameters) {
        Map<UUID, WorkoutExercise> existingExercises = workout.getExercisesAsMap();
        parameters.exercises().forEach(exercise -> {
            if (existingExercises.containsKey(exercise.id())) {
                updateExercise(exercise, existingExercises.get(exercise.id()));
                existingExercises.remove(exercise.id());
            } else {
                addExerciseToWorkout(workout, exercise);
            }
        });
        deleteRemainingExistingExercises(workout, existingExercises);
    }

    private static void deleteRemainingExistingExercises(Workout workout, Map<UUID, WorkoutExercise> existingExercises) {
        workout.getExercises().removeAll(existingExercises.values());
    }

    private void updateExercise(Parameters.ExerciseParameters parameters, WorkoutExercise workoutExercise) {
        Exercise exercise = exerciseRepository.findById(parameters.exerciseId()).orElseThrow();
        workoutExercise.setExercise(exercise);
        workoutExercise.setReps(parameters.reps());
        workoutExercise.setWeightInKg(parameters.weightInKg());
        workoutExercise.setSets(parameters.sets());
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
