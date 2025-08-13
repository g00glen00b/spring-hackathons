package codes.dimitri.apps.moviereservation.movie;

import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Showtime {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private ShowtimeId id;
    @ManyToOne
    private Movie movie;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "theatre_id"))
    private TheatreId theatreId;
    private Instant startingAt;
    private Instant endingAt;
    private BigDecimal price;

    public LocalDateTime getLocalStartingAt(ZoneId zoneId) {
        return LocalDateTime.ofInstant(startingAt, zoneId);
    }

    public boolean isStartingOn(LocalDate date, ZoneId zoneId) {
        return getLocalStartingAt(zoneId).toLocalDate().equals(date);
    }

    public boolean isInPast() {
        return endingAt.compareTo(Instant.now()) <= 0;
    }

    public BigDecimal calculateTotal(int seats) {
        return price.multiply(new BigDecimal(seats));
    }
}
