package codes.dimitri.apps.moviereservation.reservation.repository;

import codes.dimitri.apps.moviereservation.reservation.ReservationId;

public interface CustomReservationRepository {
    ReservationId nextId();
}
