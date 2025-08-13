package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import codes.dimitri.apps.moviereservation.reservation.SeatReservationState;
import codes.dimitri.apps.moviereservation.reservation.repository.ReservationRepository;
import codes.dimitri.apps.moviereservation.theatre.SeatId;
import codes.dimitri.apps.moviereservation.theatre.Theatre;
import codes.dimitri.apps.moviereservation.theatre.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListAllReservedShowtimeSeats {
    private final ReservationRepository reservationRepository;
    private final ShowtimeRepository showtimeRepository;
    private final TheatreRepository theatreRepository;

    public List<SeatReservationState> execute(ListAllReservedShowtimeSeatsParameters parameters) {
        Showtime showtime = showtimeRepository.findById(parameters.showtimeId()).orElseThrow();
        Theatre theatre = theatreRepository.findById(showtime.getTheatreId()).orElseThrow();
        List<SeatId> reservedSeatIds = reservationRepository.findAllReservedSeatIdsByShowtimeId(parameters.showtimeId());

        return theatre
            .getSeats()
            .stream()
            .map(seat -> new SeatReservationState(
                seat,
                reservedSeatIds.contains(seat.getId()),
                showtime.getPrice()
            ))
            .toList();
    }
}
