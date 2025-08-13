package codes.dimitri.apps.moviereservation.reservation;

import codes.dimitri.apps.moviereservation.theatre.Seat;

import java.math.BigDecimal;

public record SeatReservationState(Seat seat, boolean reserved, BigDecimal price) {
}
