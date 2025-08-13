package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;

public record FilterShowtimesParameters(ZoneId zoneId, MovieId movieId, LocalDate date) {
    public FilterShowtimesParameters {
        Assert.notNull(zoneId, "zoneId must not be null");
        Assert.notNull(movieId, "movieId must not be null");
        Assert.notNull(date, "date must not be null");
    }
}
