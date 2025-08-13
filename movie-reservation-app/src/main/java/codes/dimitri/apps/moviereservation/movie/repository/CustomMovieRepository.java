package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.MovieId;

public interface CustomMovieRepository {
    MovieId nextId();
}
