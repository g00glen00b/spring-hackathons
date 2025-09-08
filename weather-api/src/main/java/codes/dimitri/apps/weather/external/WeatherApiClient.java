package codes.dimitri.apps.weather.external;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/timeline")
public interface WeatherApiClient {
    @GetExchange("/{location}")
    WeatherApiResponse findByLocation(
        @PathVariable String location,
        @RequestParam String key,
        @RequestParam String elements,
        @RequestParam String unitGroup,
        @RequestParam String include);
}
