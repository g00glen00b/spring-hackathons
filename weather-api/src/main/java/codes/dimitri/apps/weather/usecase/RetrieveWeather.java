package codes.dimitri.apps.weather.usecase;

import codes.dimitri.apps.weather.external.WeatherApiClient;
import codes.dimitri.apps.weather.external.WeatherApiProperties;
import codes.dimitri.apps.weather.external.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetrieveWeather {
    private static final String WEATHER_API_ELEMENTS = "datetime,temp,conditions";
    private static final String WEATHER_API_INCLUDE = "days";
    private static final String WEATHER_API_UNIT_GROUP = "metric";
    private final WeatherApiClient weatherApiClient;
    private final WeatherApiProperties properties;

    @Cacheable("weather")
    public WeatherResponse execute(RetrieveWeatherParameters parameters) {
        log.debug("🚀 Weather API called for {}", parameters);
        WeatherApiResponse apiResponse = weatherApiClient.findByLocation(
            parameters.location(),
            properties.apiKey(),
            WEATHER_API_ELEMENTS,
            WEATHER_API_UNIT_GROUP,
            WEATHER_API_INCLUDE
        );
        return WeatherResponse.fromWeatherApi(apiResponse);
    }
}
