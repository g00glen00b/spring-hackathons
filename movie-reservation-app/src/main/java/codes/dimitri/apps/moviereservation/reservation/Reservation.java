package codes.dimitri.apps.moviereservation.reservation;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import codes.dimitri.apps.moviereservation.user.UserId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private ReservationId id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "showtime_id"))
    private ShowtimeId showtimeId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    private UserId ownerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation")
    private List<ReservationSeat> reservedSeats;

    public Reservation(ReservationId id, ShowtimeId showtimeId, UserId ownerId) {
        this(id, showtimeId, ownerId, new ArrayList<>());
    }

    public int getNumberOfSeats() {
        return reservedSeats.size();
    }
}
