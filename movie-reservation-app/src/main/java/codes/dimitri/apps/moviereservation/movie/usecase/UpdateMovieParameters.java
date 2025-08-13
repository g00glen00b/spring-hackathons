package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.GenreId;
import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

public record UpdateMovieParameters(
    MovieId id,
    String title,
    String description,
    List<GenreId> genreIds,
    MultipartFile poster,
    Duration duration
) {
    public UpdateMovieParameters {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(title, "title must not be null");
        Assert.state(!title.isEmpty(), "title must not be empty");
        Assert.state(title.length() <= 128, "title must be less than 128 characters");
        Assert.notNull(description, "description must not be null");
        Assert.state(!description.isEmpty(), "description must not be empty");
        Assert.state(description.length() <= 512, "description must be less than 512 characters");
        Assert.notEmpty(genreIds, "genreIds must not be null");
        Assert.notNull(poster, "poster must not be null");
        Assert.notNull(duration, "duration must not be null");
        Assert.state(duration.isPositive(), "duration must be positive");
    }
}
