package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;

public record ListAllReservedShowtimeSeatsParameters(ShowtimeId showtimeId) {
}
