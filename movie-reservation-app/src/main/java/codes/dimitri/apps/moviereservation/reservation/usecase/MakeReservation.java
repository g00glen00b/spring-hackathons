package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import codes.dimitri.apps.moviereservation.reservation.Reservation;
import codes.dimitri.apps.moviereservation.reservation.ReservationSeat;
import codes.dimitri.apps.moviereservation.reservation.ReservationSeatId;
import codes.dimitri.apps.moviereservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class MakeReservation {
    private final ReservationRepository reservationRepository;
    private final ShowtimeRepository showtimeRepository;

    public Reservation execute(MakeReservationParameters parameters) {
        Showtime showtime = showtimeRepository.findById(parameters.showtimeId()).orElseThrow();
        validateNotInPast(showtime);
        validateSeatNotReserved(parameters);
        Reservation reservation = new Reservation(
            reservationRepository.nextId(),
            parameters.showtimeId(),
            parameters.ownerId()
        );
        reservation.getReservedSeats().addAll(createReservationSeats(parameters, reservation));
        return reservationRepository.save(reservation);
    }

    private static List<ReservationSeat> createReservationSeats(MakeReservationParameters parameters, Reservation reservation) {
        return parameters.seats().stream().map(seatId -> new ReservationSeat(new ReservationSeatId(reservation.getId(), seatId), reservation)).toList();
    }

    private void validateNotInPast(Showtime showtime) {
        Assert.state(!showtime.isInPast(), "Showtime is in the past and cannot be reserved.");
    }

    private void validateSeatNotReserved(MakeReservationParameters parameters) {
        boolean alreadyReserved = reservationRepository.existsByShowtimeIdAndSeatId(parameters.showtimeId(), parameters.seats());
        Assert.state(!alreadyReserved, "One or more seats are already reserved for this showtime.");
    }
}
