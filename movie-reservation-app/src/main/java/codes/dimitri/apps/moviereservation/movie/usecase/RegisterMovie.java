package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Genre;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.movie.repository.GenreRepository;
import codes.dimitri.apps.moviereservation.movie.repository.MoviePosterRepository;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class RegisterMovie {
    private final MovieRepository movieRepository;
    private final MoviePosterRepository moviePosterRepository;
    private final GenreRepository genreRepository;

    public Movie execute(RegisterMovieParameters parameters) {
        MovieId id = movieRepository.nextId();
        List<Genre> genres = genreRepository.findAllById(parameters.genreIds());
        Movie movie = new Movie(
            id,
            parameters.title(),
            parameters.description(),
            parameters.duration(),
            genres
        );
        moviePosterRepository.save(id, parameters.poster());
        return movieRepository.save(movie);
    }
}
