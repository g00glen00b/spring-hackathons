package codes.dimitri.apps.weather.usecase;

import codes.dimitri.apps.weather.external.WeatherApiResponse;

import java.util.List;

public record WeatherResponse(String location, List<WeatherDayResponse> days) {
    public static WeatherResponse fromWeatherApi(WeatherApiResponse response) {
        return new WeatherResponse(
            response.resolvedAddress(),
            response.days().stream().map(WeatherDayResponse::fromWeatherApi).toList()
        );
    }
}
