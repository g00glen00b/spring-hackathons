package codes.dimitri.apps.moviereservation.movie.web;

import codes.dimitri.apps.moviereservation.movie.Genre;

import java.util.UUID;

public record GenreResponse(UUID id, String name) {
    public static GenreResponse of(Genre genre) {
        return new GenreResponse(genre.getId().getId(), genre.getName());
    }
}
