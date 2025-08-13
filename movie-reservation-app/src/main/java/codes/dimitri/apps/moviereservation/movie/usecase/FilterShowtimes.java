package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class FilterShowtimes {
    private final ShowtimeRepository showtimeRepository;

    public List<Showtime> execute(FilterShowtimesParameters parameters) {
        List<Showtime> showtimes = showtimeRepository.findAllByMovieIdAndStartingAtAfter(parameters.movieId(), Instant.now());
        return showtimes
            .stream()
            .filter(showtime -> showtime.isStartingOn(parameters.date(), parameters.zoneId()))
            .sorted(Comparator.comparing(Showtime::getStartingAt))
            .toList();
    }
}
