package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.util.Assert;

public record GetMoviePosterParameters(MovieId movieId) {
    public GetMoviePosterParameters {
        Assert.notNull(movieId, "movieId must not be null");
    }
}
