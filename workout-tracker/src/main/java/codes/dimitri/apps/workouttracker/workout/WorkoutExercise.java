package codes.dimitri.apps.workouttracker.workout;

import codes.dimitri.apps.workouttracker.exercise.Exercise;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class WorkoutExercise {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
    private int reps;
    private int sets;
    private Double weightInKg;

    public WorkoutExercise(Exercise exercise, int reps, int sets, Double weightInKg) {
        this(UUID.randomUUID(), exercise, null, reps, sets, weightInKg);
    }
}
