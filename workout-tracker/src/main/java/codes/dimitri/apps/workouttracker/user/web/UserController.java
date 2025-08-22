package codes.dimitri.apps.workouttracker.user.web;

import codes.dimitri.apps.workouttracker.security.JwtUser;
import codes.dimitri.apps.workouttracker.user.usecase.RegisterUser;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUser registerUser;

    @GetMapping("/token")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public TokenDTO generateToken(@AuthenticationPrincipal JwtUser user) {
        return new TokenDTO(user.jwt());
    }

    @GetMapping("/info")
    public UserDTO getUserInfo(@AuthenticationPrincipal DecodedJWT jwt) {
        return UserDTO.of(jwt);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirements
    public void registerUser(@RequestBody RegisterUserRequestDTO request) {
        registerUser.execute(request.toParameters());
    }
}
