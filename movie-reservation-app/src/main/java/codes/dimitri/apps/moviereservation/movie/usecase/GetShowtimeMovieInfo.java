package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class GetShowtimeMovieInfo {
    private final ShowtimeRepository showtimeRepository;

    public Movie execute(GetShowtimeMovieInfoParameters parameters) {
        return showtimeRepository
            .findById(parameters.showtimeId())
            .map(Showtime::getMovie)
            .orElseThrow();
    }
}
