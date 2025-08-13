package codes.dimitri.apps.moviereservation.theatre.usecase;

import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import org.springframework.util.Assert;

public record GetTheatreInfoParameters(TheatreId theatreId) {
    public GetTheatreInfoParameters {
        Assert.notNull(theatreId, "theatreId must not be null");
    }
}
