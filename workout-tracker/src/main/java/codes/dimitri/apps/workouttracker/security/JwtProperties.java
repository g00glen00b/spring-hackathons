package codes.dimitri.apps.workouttracker.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, @DefaultValue("1h") Duration expiration, String issuer) {
}
