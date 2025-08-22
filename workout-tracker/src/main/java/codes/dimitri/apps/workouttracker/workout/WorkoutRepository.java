package codes.dimitri.apps.workouttracker.workout;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
    Optional<Workout> findByIdAndUserId(UUID id, UUID userId);
    Page<Workout> findAllByUserId(UUID userId, Pageable pageable);
}
