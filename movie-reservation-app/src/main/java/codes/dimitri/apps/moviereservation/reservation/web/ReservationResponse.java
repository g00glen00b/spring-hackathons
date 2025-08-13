package codes.dimitri.apps.moviereservation.reservation.web;

import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.reservation.ReservationSeat;
import codes.dimitri.apps.moviereservation.reservation.ReservationSeatId;
import codes.dimitri.apps.moviereservation.reservation.usecase.ReservationShowtimeTheatreTuple;
import codes.dimitri.apps.moviereservation.theatre.Seat;
import codes.dimitri.apps.moviereservation.theatre.SeatId;
import codes.dimitri.apps.moviereservation.theatre.Theatre;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ReservationResponse(
    UUID id,
    ReservationMovieResponse movie,
    ReservationShowtimeResponse showtime,
    ReservationTheatreResponse theatre,
    List<ReservationSeatResponse> reservedSeats,
    BigDecimal totalPrice) {
    public record ReservationMovieResponse(UUID id, String title) {
        public static ReservationMovieResponse of(Movie movie) {
            return new ReservationMovieResponse(
                movie.getId().getId(),
                movie.getTitle()
            );
        }
    }

    public record ReservationShowtimeResponse(UUID id, Instant startingAt) {
        public static ReservationShowtimeResponse of(Showtime showtime) {
            return new ReservationShowtimeResponse(
                showtime.getId().getId(),
                showtime.getStartingAt()
            );
        }
    }

    public record ReservationTheatreResponse(UUID id, String name) {
        public static ReservationTheatreResponse of(Theatre theatre) {
            return new ReservationTheatreResponse(
                theatre.getId().getId(),
                theatre.getName()
            );
        }
    }

    public record ReservationSeatResponse(UUID id, String number) {
        public static ReservationSeatResponse of(Seat seat) {
            return new ReservationSeatResponse(
                seat.getId().getId(),
                seat.getNumber()
            );
        }
    }

    public static ReservationResponse of(ReservationShowtimeTheatreTuple tuple) {
        Map<SeatId, Seat> seatMap = tuple.getTheatre().getSeatMap();
        return new ReservationResponse(
            tuple.getReservation().getId().getId(),
            ReservationMovieResponse.of(tuple.getShowtime().getMovie()),
            ReservationShowtimeResponse.of(tuple.getShowtime()),
            ReservationTheatreResponse.of(tuple.getTheatre()),
            tuple.getReservation().getReservedSeats()
                .stream()
                .map(ReservationSeat::getId)
                .map(ReservationSeatId::getSeatId)
                .map(seatMap::get)
                .map(ReservationSeatResponse::of)
                .toList(),
            tuple.getShowtime().calculateTotal(tuple.getReservation().getNumberOfSeats())
        );
    }
}
