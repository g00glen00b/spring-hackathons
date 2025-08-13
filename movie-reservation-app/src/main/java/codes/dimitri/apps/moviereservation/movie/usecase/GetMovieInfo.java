package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class GetMovieInfo {
    private final MovieRepository movieRepository;

    public Movie execute(GetMovieInfoParameters parameters) {
        return movieRepository.findById(parameters.movieId()).orElseThrow();
    }
}
