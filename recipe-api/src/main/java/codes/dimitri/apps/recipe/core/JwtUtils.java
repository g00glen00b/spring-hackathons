package codes.dimitri.apps.recipe.core;

import codes.dimitri.apps.recipe.chef.Chef;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    public static final String USERNAME_CLAIM = "unm";
    public static final String DISPLAYNAME_CLAIM = "dnm";
    private final Algorithm algorithm;
    private final JwtProperties properties;

    public String token(Chef chef) {
        return JWT
            .create()
            .withIssuer(properties.issuer())
            .withSubject(chef.getId().toString())
            .withExpiresAt(Instant.now().plus(properties.expiration()))
            .withClaim(USERNAME_CLAIM, chef.getUsername())
            .withClaim(DISPLAYNAME_CLAIM, chef.getDisplayname())
            .withIssuedAt(Instant.now())
            .sign(algorithm);
    }
}
