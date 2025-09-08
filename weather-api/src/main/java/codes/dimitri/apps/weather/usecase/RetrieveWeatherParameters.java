package codes.dimitri.apps.weather.usecase;

import org.springframework.util.Assert;

public record RetrieveWeatherParameters(String location) {
    public RetrieveWeatherParameters {
        Assert.notNull(location, "location must not be null");
    }
}
