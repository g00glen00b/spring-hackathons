package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import codes.dimitri.apps.moviereservation.reservation.NavigationState;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class NavigateReservation {
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    public NavigationState execute(NavigateReservationParameters parameters) {
        if (parameters.movieId() == null && parameters.showtimeId() == null) return NavigationState.empty();
        if (parameters.showtimeId() != null) {
            Showtime showtime = showtimeRepository.findById(parameters.showtimeId()).orElseThrow();
            LocalDateTime dateTime = showtime.getLocalStartingAt(parameters.zoneId());
            return NavigationState.of(showtime.getMovie(), dateTime);
        }
        Movie movie = movieRepository.findById(parameters.movieId()).orElseThrow();
        if (parameters.date() == null) return NavigationState.of(movie);
        return NavigationState.of(movie, parameters.date());
    }
}
