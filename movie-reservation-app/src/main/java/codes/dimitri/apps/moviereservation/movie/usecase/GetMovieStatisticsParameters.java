package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.util.Assert;

public record GetMovieStatisticsParameters(MovieId movieId) {
    public GetMovieStatisticsParameters {
        Assert.notNull(movieId, "movieId must not be null");
    }
}
