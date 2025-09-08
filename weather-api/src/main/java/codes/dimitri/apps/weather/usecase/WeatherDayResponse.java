package codes.dimitri.apps.weather.usecase;

import codes.dimitri.apps.weather.external.WeatherApiDayResponse;

import java.time.LocalDate;

public record WeatherDayResponse(Double temperature, LocalDate date, String conditions) {
    public static WeatherDayResponse fromWeatherApi(WeatherApiDayResponse response) {
        return new WeatherDayResponse(
            response.temperature(),
            response.datetime(),
            response.conditions()
        );
    }
}
