package codes.dimitri.apps.workouttracker.workout.web;

import codes.dimitri.apps.workouttracker.workout.WorkoutSchedule;
import codes.dimitri.apps.workouttracker.workout.usecase.AddSchedule;
import codes.dimitri.apps.workouttracker.workout.usecase.DeleteSchedule;
import codes.dimitri.apps.workouttracker.workout.usecase.UpdateSchedule;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutScheduleController {
    private final AddSchedule addSchedule;
    private final UpdateSchedule updateSchedule;
    private final DeleteSchedule deleteSchedule;

    @PostMapping("/{id}/schedule")
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable UUID id, @AuthenticationPrincipal DecodedJWT jwt) {
        var userId = UUID.fromString(jwt.getSubject());
        deleteSchedule.execute(new DeleteSchedule.Parameters(id, userId));
    }
}
