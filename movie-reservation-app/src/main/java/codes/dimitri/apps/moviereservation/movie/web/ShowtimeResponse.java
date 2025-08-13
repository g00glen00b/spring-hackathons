package codes.dimitri.apps.moviereservation.movie.web;

import codes.dimitri.apps.moviereservation.movie.Showtime;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ShowtimeResponse(UUID id, Instant startingAt, UUID theatreId, BigDecimal price) {
    public static ShowtimeResponse of(Showtime showtime) {
        return new ShowtimeResponse(
            showtime.getId().getId(),
            showtime.getStartingAt(),
            showtime.getTheatreId().getId(),
            showtime.getPrice()
        );
    }
}
