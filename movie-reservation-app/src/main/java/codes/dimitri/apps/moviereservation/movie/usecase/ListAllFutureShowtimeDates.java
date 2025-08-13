package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListAllFutureShowtimeDates {
    private final ShowtimeRepository showtimeRepository;

    public List<LocalDate> execute(ListAllFutureShowtimeDatesParameters parameters) {
        List<Showtime> showtimes = showtimeRepository.findAllByMovieIdAndStartingAtAfter(parameters.movieId(), Instant.now());
        return showtimes
            .stream()
            .map(showtime -> showtime.getLocalStartingAt(parameters.zoneId()))
            .map(LocalDateTime::toLocalDate)
            .distinct()
            .sorted()
            .toList();
    }
}
