package codes.dimitri.apps.moviereservation.theatre;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theatre {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private TheatreId id;
    private String name;
    @OneToMany(mappedBy = "theatre")
    private List<Seat> seats;

    public Map<SeatId, Seat> getSeatMap() {
        return seats.stream().collect(toMap(Seat::getId, identity()));
    }
}
