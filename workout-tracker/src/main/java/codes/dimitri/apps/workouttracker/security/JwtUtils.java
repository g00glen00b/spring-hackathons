package codes.dimitri.apps.workouttracker.security;

import codes.dimitri.apps.workouttracker.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final Algorithm algorithm;
    private final JwtProperties properties;

    public String token(User user) {
        return JWT
            .create()
            .withIssuer(properties.issuer())
            .withSubject(user.getId().toString())
            .withExpiresAt(Instant.now().plus(properties.expiration()))
            .withClaim("username", user.getUsername())
            .withIssuedAt(Instant.now())
            .sign(algorithm);
    }
}
