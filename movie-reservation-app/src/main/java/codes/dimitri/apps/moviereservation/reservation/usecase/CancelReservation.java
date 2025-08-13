package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import codes.dimitri.apps.moviereservation.reservation.Reservation;
import codes.dimitri.apps.moviereservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@UseCase
@RequiredArgsConstructor
public class CancelReservation {
    private final ReservationRepository reservationRepository;
    private final ShowtimeRepository showtimeRepository;

    public void execute(CancelReservationParameters parameters) {
        Reservation reservation = reservationRepository.findById(parameters.reservationId()).orElseThrow();
        validateReservationNotInPast(reservation);
        reservationRepository.delete(reservation);
    }

    private void validateReservationNotInPast(Reservation reservation) {
        Showtime showtime = showtimeRepository.findById(reservation.getShowtimeId()).orElseThrow();
        Assert.state(!showtime.isInPast(), "Cannot cancel a reservation for a showtime that is in the past.");
    }
}
