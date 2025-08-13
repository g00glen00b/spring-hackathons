package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListAllFutureUserReservations {
    private final ReservationRepository reservationRepository;

    public List<ReservationShowtimeTheatreTuple> execute(ListAllFutureUserReservationsParameters parameters) {
        return reservationRepository.findAllByOwnerIdAndShowtimeStartingAtAfter(parameters.ownerId(), Instant.now());
    }
}
