package codes.dimitri.apps.workouttracker.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, UUID> {
    @Query("select s from WorkoutSchedule s inner join s.workout w where s.id = ?1 and w.userId = ?2")
    Optional<WorkoutSchedule> findByIdAndUserId(UUID id, UUID userId);
}
