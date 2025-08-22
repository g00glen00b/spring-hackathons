package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.Workout;
import codes.dimitri.apps.workouttracker.workout.usecase.CreateWorkout;
import codes.dimitri.apps.workouttracker.workout.usecase.DeleteWorkout;
import codes.dimitri.apps.workouttracker.workout.usecase.GetWorkoutReport;
import codes.dimitri.apps.workouttracker.workout.usecase.ListWorkouts;
import codes.dimitri.apps.workouttracker.workout.usecase.UpdateWorkout;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {
    private final CreateWorkout createWorkout;
    private final UpdateWorkout updateWorkout;
    private final DeleteWorkout deleteWorkout;
    private final ListWorkouts listWorkouts;
    private final GetWorkoutReport getWorkoutReport;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
            .execute(new ListWorkouts.Parameters(date, zoneId, userId, pageable))
            .map(WorkoutDTO::of);
    }

    @GetMapping("/{id}/report")
    public WorkoutReportDTO getWorkoutReport(
        @PathVariable UUID id,
        @AuthenticationPrincipal DecodedJWT jwt) {
        var userId = UUID.fromString(jwt.getSubject());
        var report = getWorkoutReport.execute(new GetWorkoutReport.Parameters(id, userId));
        return WorkoutReportDTO.from(report);
    }
}
