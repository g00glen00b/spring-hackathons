package codes.dimitri.apps.weather.external;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "weather.api")
public record WeatherApiProperties(
    @DefaultValue("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services") String baseUrl,
    String apiKey) {
}
