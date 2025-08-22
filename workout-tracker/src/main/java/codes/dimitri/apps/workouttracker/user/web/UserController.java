package codes.dimitri.apps.workouttracker.user.web;

import codes.dimitri.apps.workouttracker.security.JwtUser;
import codes.dimitri.apps.workouttracker.user.usecase.RegisterUser;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUser registerUser;

    @PostMapping("/token")
    public TokenDTO generateToken(@AuthenticationPrincipal JwtUser user) {
        return new TokenDTO(user.jwt());
    }

    @GetMapping("/info")
    public UserDTO getUserInfo(@AuthenticationPrincipal DecodedJWT jwt) {
        return UserDTO.of(jwt);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterUserRequestDTO request) {
        registerUser.execute(request.toParameters());
    }
}
