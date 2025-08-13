package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.reservation.Reservation;
import codes.dimitri.apps.moviereservation.theatre.Theatre;

public interface ReservationShowtimeTheatreTuple {
    Reservation getReservation();
    Showtime getShowtime();
    Theatre getTheatre();
}
