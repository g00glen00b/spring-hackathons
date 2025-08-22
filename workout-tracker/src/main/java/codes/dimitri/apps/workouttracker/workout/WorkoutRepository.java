package codes.dimitri.apps.workouttracker.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID>, JpaSpecificationExecutor<Workout> {
    Optional<Workout> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByIdAndUserId(UUID id, UUID userId);
}
