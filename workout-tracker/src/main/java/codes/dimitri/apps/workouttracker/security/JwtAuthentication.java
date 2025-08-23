package codes.dimitri.apps.workouttracker.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.List;

@Getter
public class JwtAuthentication extends PreAuthenticatedAuthenticationToken {
    @Builder
    public JwtAuthentication(DecodedJWT jwt, WebAuthenticationDetails details) {
        super(jwt, jwt, List.of());
        super.setDetails(details);
    }
}
