package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.GenreId;
import codes.dimitri.apps.moviereservation.movie.Movie;
import org.springframework.data.jpa.domain.Specification;

public final class MovieSpecifications {
    public static Specification<Movie> withGenreId(GenreId genreId) {
        if (genreId == null) return null;
        return (root, query, builder) -> builder.equal(root.get("genres").get("id"), genreId);
    }
}
