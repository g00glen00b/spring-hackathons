package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.MovieId;

import java.util.UUID;

class CustomMovieRepositoryImpl implements CustomMovieRepository {
    @Override
    public MovieId nextId() {
        return new MovieId(UUID.randomUUID());
    }
}
