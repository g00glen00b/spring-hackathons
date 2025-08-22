package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.WorkoutSchedule;
import codes.dimitri.apps.workouttracker.workout.usecase.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {
    private final CreateWorkout createWorkout;
    private final UpdateWorkout updateWorkout;
    private final DeleteWorkout deleteWorkout;
    private final ListWorkouts listWorkouts;
    private final AddSchedule addSchedule;
    private final UpdateSchedule updateSchedule;
    private final DeleteSchedule deleteSchedule;

    @PostMapping
    public WorkoutDTO createWorkout(
        @RequestBody CreateWorkoutRequestDTO request,
        @AuthenticationPrincipal DecodedJWT jwt) {
        var userId = UUID.fromString(jwt.getSubject());
        Workout workout = createWorkout.execute(request.toParameters(userId));
        return WorkoutDTO.of(workout);
    }

    @PutMapping("/{id}")
    public WorkoutDTO updateWorkout(
        @PathVariable UUID id,
        @RequestBody UpdateWorkoutRequestDTO request,
        @AuthenticationPrincipal DecodedJWT jwt) {
        var userId = UUID.fromString(jwt.getSubject());
        Workout workout = updateWorkout.execute(request.toParameters(id, userId));
        return WorkoutDTO.of(workout);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable UUID id, @AuthenticationPrincipal DecodedJWT jwt) {
        var userId = UUID.fromString(jwt.getSubject());
        deleteWorkout.execute(new DeleteWorkout.Parameters(id, userId));
    }

    @GetMapping
    public Page<WorkoutDTO> listWorkouts(
        @AuthenticationPrincipal DecodedJWT jwt,
        @RequestParam(required = false) LocalDate date,
        @RequestParam(required = false) ZoneId zoneId,
        Pageable pageable) {
        var userId = UUID.fromString(jwt.getSubject());
        return listWorkouts
            .execute(new ListWorkouts.Parameters(userId, pageable))
            .map(WorkoutDTO::of);
    }

    @PostMapping("/{id}/schedule")
    public WorkoutScheduleDTO addSchedule(
        @PathVariable UUID id,
        @RequestBody AddWorkoutScheduleRequestDTO request,
        @AuthenticationPrincipal DecodedJWT jwt
    ) {
        var userId = UUID.fromString(jwt.getSubject());
        WorkoutSchedule schedule = addSchedule.execute(request.toParameters(id, userId));
        return WorkoutScheduleDTO.of(schedule);
    }

    @PutMapping("/schedule/{id}")
    public WorkoutScheduleDTO updateSchedule(
        @PathVariable UUID id,
        @RequestBody UpdateWorkoutScheduleRequestDTO request,
        @AuthenticationPrincipal DecodedJWT jwt
    ) {
        var userId = UUID.fromString(jwt.getSubject());
        WorkoutSchedule schedule = updateSchedule.execute(request.toParameters(id, userId));
        return WorkoutScheduleDTO.of(schedule);
    }

    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable UUID id, @AuthenticationPrincipal DecodedJWT jwt) {
        var userId = UUID.fromString(jwt.getSubject());
        deleteSchedule.execute(new DeleteSchedule.Parameters(id, userId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public void handleNoSuchElement() {}

}
