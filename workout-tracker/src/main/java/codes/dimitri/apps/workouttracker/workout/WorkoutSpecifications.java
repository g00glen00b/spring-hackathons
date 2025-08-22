package codes.dimitri.apps.workouttracker.workout;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class WorkoutSpecifications {
    public static Specification<Workout> userId(UUID userId) {
        if (userId == null) return Specification.unrestricted();
        return (root, query, cb) -> cb.equal(root.get("userId"), userId);
    }

    public static Specification<Workout> plannedBetween(Instant start, Instant end) {
        if (start == null || end == null) return Specification.unrestricted();
        return (root, query, cb) -> {
            Subquery<WorkoutSchedule> subquery = Objects.requireNonNull(query).subquery(WorkoutSchedule.class);
            Root<WorkoutSchedule> schedule = subquery.from(WorkoutSchedule.class);
            return cb.exists(subquery.select(schedule.get("workout")).where(cb.and(
                cb.greaterThanOrEqualTo(schedule.get("plannedStart"), start),
                cb.lessThanOrEqualTo(schedule.get("plannedStart"), end)
            )));
        };
    }
}
