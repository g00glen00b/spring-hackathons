package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.reservation.ReservationId;
import codes.dimitri.apps.moviereservation.user.UserId;

public record CancelReservationParameters(ReservationId reservationId, UserId ownerId) {
}
