package codes.dimitri.apps.weather.external;

import java.util.List;

public record WeatherApiResponse(
    List<WeatherApiDayResponse> days,
    String resolvedAddress
) {
}
