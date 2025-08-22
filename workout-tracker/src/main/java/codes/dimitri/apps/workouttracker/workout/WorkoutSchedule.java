package codes.dimitri.apps.workouttracker.workout;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class WorkoutSchedule {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
    private Instant plannedStart;
    private Instant actualStart;
    private Instant actualEnd;

    public WorkoutSchedule(Workout workout, Instant plannedStart) {
        this(UUID.randomUUID(), workout, plannedStart, null, null);
    }
}
