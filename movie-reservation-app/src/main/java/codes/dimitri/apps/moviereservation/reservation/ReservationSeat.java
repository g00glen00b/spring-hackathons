package codes.dimitri.apps.moviereservation.reservation;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSeat {
    @EmbeddedId
    private ReservationSeatId id;
    @MapsId("reservationId")
    @ManyToOne
    private Reservation reservation;
}
