package codes.dimitri.apps.workouttracker.user.web;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;

public record UserDTO(String iss, String sub, String username, Instant exp, Instant iat) {
    public static UserDTO of(DecodedJWT jwt) {
        return new UserDTO(
            jwt.getIssuer(),
            jwt.getSubject(),
            jwt.getClaim("username").asString(),
            jwt.getExpiresAtAsInstant(),
            jwt.getIssuedAtAsInstant()
        );
    }
}
