package codes.dimitri.apps.moviereservation.reservation.usecase;

import codes.dimitri.apps.moviereservation.user.UserId;
import org.springframework.util.Assert;

public record ListAllFutureUserReservationsParameters(UserId ownerId) {
    public ListAllFutureUserReservationsParameters {
        Assert.notNull(ownerId, "User ID must not be null");
    }
}
