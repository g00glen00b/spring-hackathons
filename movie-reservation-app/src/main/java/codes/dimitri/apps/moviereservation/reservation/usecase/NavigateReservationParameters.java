package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;

public record NavigateReservationParameters(MovieId movieId, LocalDate date, ShowtimeId showtimeId, ZoneId zoneId) {
    public NavigateReservationParameters {
        Assert.notNull(zoneId, "zoneId must not be null");
    }
}
