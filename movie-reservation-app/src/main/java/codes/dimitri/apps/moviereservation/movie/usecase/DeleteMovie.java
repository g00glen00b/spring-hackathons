package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.repository.MoviePosterRepository;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteMovie {
    private final MovieRepository movieRepository;
    private final MoviePosterRepository moviePosterRepository;

    public void execute(DeleteMovieParameters parameters) {
        movieRepository.deleteById(parameters.movieId());
        moviePosterRepository.deleteById(parameters.movieId());
    }
}
