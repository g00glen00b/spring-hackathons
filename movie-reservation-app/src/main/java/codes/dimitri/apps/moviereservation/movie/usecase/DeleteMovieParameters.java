package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.util.Assert;

public record DeleteMovieParameters(MovieId movieId) {
    public DeleteMovieParameters {
        Assert.notNull(movieId, "movieId must not be null");
    }
}
