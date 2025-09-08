package codes.dimitri.apps.weather.web;

import codes.dimitri.apps.weather.usecase.RetrieveWeather;
import codes.dimitri.apps.weather.usecase.RetrieveWeatherParameters;
import codes.dimitri.apps.weather.usecase.WeatherResponse;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
    private static final int NORMAL_REQUEST_TOKEN_COUNT = 1;
    private final RetrieveWeather retrieveWeather;
    private final Bucket bucket;

    @GetMapping
    public WeatherResponse findByLocation(@RequestParam String location) {
        validateRatelimit();
        var parameters = new RetrieveWeatherParameters(location);
        return retrieveWeather.execute(parameters);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ErrorResponse handleTooManyRequests(TooManyRequestsException ex) {
        return ErrorResponse.create(
            ex,
            HttpStatus.TOO_MANY_REQUESTS,
            "Too many requests, try again later"
        );
    }

    private void validateRatelimit() {
        if (!bucket.tryConsume(NORMAL_REQUEST_TOKEN_COUNT)) {
            throw new TooManyRequestsException();
        }
    }
}
