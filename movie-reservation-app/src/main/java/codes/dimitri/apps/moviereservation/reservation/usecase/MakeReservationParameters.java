package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import codes.dimitri.apps.moviereservation.theatre.SeatId;
import codes.dimitri.apps.moviereservation.user.UserId;
import org.springframework.util.Assert;

import java.util.List;

public record MakeReservationParameters(UserId ownerId, ShowtimeId showtimeId, List<SeatId> seats) {
    public MakeReservationParameters {
        Assert.notNull(ownerId, "ownerId must not be null");
        Assert.notNull(showtimeId, "showtimeId must not be null");
        Assert.notEmpty(seats, "seats must not be empty");
    }
}
