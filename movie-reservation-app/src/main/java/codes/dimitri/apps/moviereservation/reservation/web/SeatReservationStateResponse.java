package codes.dimitri.apps.moviereservation.reservation.web;

import codes.dimitri.apps.moviereservation.reservation.SeatReservationState;

import java.math.BigDecimal;
import java.util.UUID;

public record SeatReservationStateResponse(UUID id, String number, boolean reserved, BigDecimal price) {
    public static SeatReservationStateResponse of(SeatReservationState seatReservationState) {
        return new SeatReservationStateResponse(
            seatReservationState.seat().getId().getId(),
            seatReservationState.seat().getNumber(),
            seatReservationState.reserved(),
            seatReservationState.price()
        );
    }
}
