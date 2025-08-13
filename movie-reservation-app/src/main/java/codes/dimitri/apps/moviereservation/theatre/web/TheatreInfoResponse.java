package codes.dimitri.apps.moviereservation.theatre.web;

import codes.dimitri.apps.moviereservation.theatre.Theatre;

import java.util.UUID;

public record TheatreInfoResponse(UUID id, String name) {
    public static TheatreInfoResponse of(Theatre theatre) {
        return new TheatreInfoResponse(theatre.getId().getId(), theatre.getName());
    }
}
