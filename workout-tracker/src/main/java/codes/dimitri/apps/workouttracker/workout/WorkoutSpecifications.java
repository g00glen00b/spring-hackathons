package codes.dimitri.apps.workouttracker.workout;

import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public class WorkoutSpecifications {
    public static Specification<Workout> userId(UUID userId) {
        if (userId == null) return Specification.unrestricted();
        return (root, query, cb) -> cb.equal(root.get("userId"), userId);
    }

    public static Specification<Workout> plannedBetween(Instant start, Instant end) {
        if (start == null || end == null) return Specification.unrestricted();
        return (root, query, cb) -> {
            Subquery<WorkoutSchedule> subquery = query.subquery(WorkoutSchedule.class);
            subquery.from(WorkoutSchedule.class)

        };
    }
}
