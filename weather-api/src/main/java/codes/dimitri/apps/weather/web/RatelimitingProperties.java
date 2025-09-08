package codes.dimitri.apps.weather.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties("weather.rate-limit")
record RatelimitingProperties(
    @DefaultValue("5") int capacity,
    @DefaultValue("1m") Duration period) {
}
