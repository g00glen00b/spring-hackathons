package codes.dimitri.apps.workouttracker.workout;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Workout {
    @Id
    private UUID id;
    private String name;
    private UUID userId;
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutExercise> exercises;

    public Workout(String name, UUID userId) {
        this(UUID.randomUUID(), name, userId, new ArrayList<>());
    }

    public void addExercise(WorkoutExercise exercise) {
        exercises.add(exercise);
        exercise.setWorkout(this);
    }

    public Map<UUID, WorkoutExercise> getExercisesAsMap() {
        return exercises.stream().collect(Collectors.toMap(WorkoutExercise::getId, identity()));
    }
}
