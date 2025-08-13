package codes.dimitri.apps.moviereservation.theatre;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private SeatId id;
    private String number;
    @ManyToOne
    private Theatre theatre;
}
