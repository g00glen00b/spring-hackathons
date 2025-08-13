package codes.dimitri.apps.moviereservation.movie.web;

import codes.dimitri.apps.moviereservation.movie.Genre;
import codes.dimitri.apps.moviereservation.movie.Movie;

import java.util.List;
import java.util.UUID;

public record MovieResponse(UUID id, String title, String description, List<String> genres) {
    public static MovieResponse of(Movie movie) {
        return new MovieResponse(
            movie.getId().getId(),
            movie.getTitle(),
            movie.getDescription(),
            movie.getGenres().stream().map(Genre::getName).toList()
        );
    }
}
