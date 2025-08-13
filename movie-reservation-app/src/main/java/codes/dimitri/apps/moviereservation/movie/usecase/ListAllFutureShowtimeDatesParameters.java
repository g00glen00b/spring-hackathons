package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.util.Assert;

import java.time.ZoneId;

public record ListAllFutureShowtimeDatesParameters(ZoneId zoneId, MovieId movieId) {
    public ListAllFutureShowtimeDatesParameters {
        Assert.notNull(zoneId, "zoneId must not be null");
        Assert.notNull(movieId, "movieId must not be null");
    }
}
