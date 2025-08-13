package codes.dimitri.apps.moviereservation.movie.web;

import codes.dimitri.apps.moviereservation.movie.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public record FilterMoviesResponse(int totalPages, int page, boolean hasPrevious, boolean hasNext, List<MovieResponse> movies) {

    public static FilterMoviesResponse of(Page<Movie> moviePage) {
        return new FilterMoviesResponse(
            moviePage.getTotalPages(),
            moviePage.getNumber(),
            moviePage.hasPrevious(),
            moviePage.hasNext(),
            moviePage.map(MovieResponse::of).toList()
        );
    }

}
