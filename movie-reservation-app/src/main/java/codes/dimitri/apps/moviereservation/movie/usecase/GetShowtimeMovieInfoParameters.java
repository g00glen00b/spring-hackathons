package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import org.springframework.util.Assert;

public record GetShowtimeMovieInfoParameters(ShowtimeId showtimeId) {
    public GetShowtimeMovieInfoParameters {
        Assert.notNull(showtimeId, "showtimeId must not be null");
    }
}
