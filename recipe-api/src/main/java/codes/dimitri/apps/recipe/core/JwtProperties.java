package codes.dimitri.apps.recipe.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
record JwtProperties(String secret, @DefaultValue("1h") Duration expiration, String issuer) {
}
