package codes.dimitri.apps.moviereservation.reservation.repository;

import codes.dimitri.apps.moviereservation.reservation.ReservationId;

import java.util.UUID;

class CustomReservationRepositoryImpl implements CustomReservationRepository {
    @Override
    public ReservationId nextId() {
        return new ReservationId(UUID.randomUUID());
    }
}
