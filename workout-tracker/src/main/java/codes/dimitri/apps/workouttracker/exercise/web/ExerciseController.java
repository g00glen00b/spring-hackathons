package codes.dimitri.apps.workouttracker.exercise.web;

import codes.dimitri.apps.workouttracker.exercise.usecase.ListAllExercises;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {
    private final ListAllExercises listAllExercises;

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "jwtAuth"))
    public Page<ExerciseDTO> getAll(@ParameterObject Pageable pageable) {
        return listAllExercises
            .execute(new ListAllExercises.Parameters(pageable))
            .map(ExerciseDTO::of);
    }
}
