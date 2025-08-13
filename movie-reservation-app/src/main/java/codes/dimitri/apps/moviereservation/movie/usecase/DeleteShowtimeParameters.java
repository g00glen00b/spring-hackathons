package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import org.springframework.util.Assert;

public record DeleteShowtimeParameters(ShowtimeId showtimeId) {
    public DeleteShowtimeParameters {
        Assert.notNull(showtimeId, "showtimeId must not be null");
    }
}
