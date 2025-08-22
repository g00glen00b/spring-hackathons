package codes.dimitri.apps.workouttracker.user.web;

import codes.dimitri.apps.workouttracker.security.JwtUser;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/token")
    public TokenDTO generateToken(@AuthenticationPrincipal JwtUser user) {
        return new TokenDTO(user.jwt());
    }

    @GetMapping("/info")
    public UserDTO getUserInfo(@AuthenticationPrincipal DecodedJWT jwt) {
        return UserDTO.of(jwt);
    }
}
