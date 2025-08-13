package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.GenreId;
import org.springframework.util.Assert;

public record FilterMoviesParameters(GenreId genreId, int page, int size) {
    public FilterMoviesParameters {
        Assert.state(page >= 0, "page must be greater or equal than 0");
        Assert.state(size > 0, "size must be greater than 0");
    }
}
