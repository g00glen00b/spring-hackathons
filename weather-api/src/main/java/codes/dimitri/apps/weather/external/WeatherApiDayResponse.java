package codes.dimitri.apps.weather.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record WeatherApiDayResponse(
    LocalDate datetime,
    @JsonProperty("temp") Double temperature,
    String conditions
) {
}
