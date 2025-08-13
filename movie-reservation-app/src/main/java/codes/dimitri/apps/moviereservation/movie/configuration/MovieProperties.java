package codes.dimitri.apps.moviereservation.movie.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "movie")
public record MovieProperties(
    @DefaultValue("file:./posters/") Resource postersLocation
) {
}
