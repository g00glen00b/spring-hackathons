package codes.dimitri.apps.moviereservation.reservation;

import codes.dimitri.apps.moviereservation.theatre.SeatId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ReservationSeatId implements Serializable {
    private ReservationId reservationId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "seat_id"))
    private SeatId seatId;
}
