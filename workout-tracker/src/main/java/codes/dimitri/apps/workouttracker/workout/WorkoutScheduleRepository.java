package codes.dimitri.apps.workouttracker.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, UUID> {
    @Query("select s from WorkoutSchedule s inner join s.workout w where s.id = ?1 and w.userId = ?2")
    Optional<WorkoutSchedule> findByIdAndUserId(UUID id, UUID userId);
    int countAllByWorkoutIdAndActualEndIsNotNull(UUID workoutId);
    @Query(value = "select sum(extract(epoch from s.actual_end - s.actual_start)) from workout_schedule s where s.workout_id = ?1 and s.actual_end is not null", nativeQuery = true)
    Long totalActualDurationByWorkoutId(UUID workoutId);
    @Query(value = "select min(extract(epoch from s.actual_end - s.actual_start)) from workout_schedule s where s.workout_id = ?1 and s.actual_end is not null", nativeQuery = true)
    Long minActualDurationByWorkoutId(UUID workoutId);
    @Query(value = "select max(extract(epoch from s.actual_end - s.actual_start)) from workout_schedule s where s.workout_id = ?1 and s.actual_end is not null", nativeQuery = true)
    Long maxActualDurationByWorkoutId(UUID workoutId);
}
