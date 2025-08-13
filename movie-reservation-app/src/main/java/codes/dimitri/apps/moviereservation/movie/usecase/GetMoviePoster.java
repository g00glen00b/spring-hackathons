package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.repository.MoviePosterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class GetMoviePoster {
    private final MoviePosterRepository moviePosterRepository;

    public Resource execute(GetMoviePosterParameters parameters) {
        return moviePosterRepository.findById(parameters.movieId());
    }
}
