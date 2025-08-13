package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Instant;

public record AddShowtimeToMovieParameters(MovieId movieId, TheatreId theatreId, Instant startingAt, BigDecimal price) {
    public AddShowtimeToMovieParameters {
        Assert.notNull(movieId, "movieId must not be null");
        Assert.notNull(theatreId, "theatreId must not be null");
        Assert.notNull(startingAt, "startingAt must not be null");
        Assert.state(startingAt.isAfter(Instant.now()), "startingAt must be after now");
        Assert.notNull(price, "price must not be null");
        Assert.state(price.compareTo(BigDecimal.ZERO) > 0, "price must be greater than zero");
    }
}
