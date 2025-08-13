package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Genre;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.repository.GenreRepository;
import codes.dimitri.apps.moviereservation.movie.repository.MoviePosterRepository;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class UpdateMovie {
    private final MovieRepository movieRepository;
    private final MoviePosterRepository moviePosterRepository;
    private final GenreRepository genreRepository;

    public Movie execute(UpdateMovieParameters parameters) {
        Movie movie = movieRepository.findById(parameters.id()).orElseThrow();
        List<Genre> genres = genreRepository.findAllById(parameters.genreIds());
        movie.setTitle(parameters.title());
        movie.setDescription(parameters.description());
        movie.setDuration(parameters.duration());
        movie.setGenres(genres);
        moviePosterRepository.save(movie.getId(), parameters.poster());
        return movie;
    }
}
