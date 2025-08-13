package codes.dimitri.apps.moviereservation.theatre.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.theatre.Theatre;
import codes.dimitri.apps.moviereservation.theatre.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class GetTheatreInfo {
    private final TheatreRepository theatreRepository;

    public Theatre execute(GetTheatreInfoParameters parameters) {
        return theatreRepository.findById(parameters.theatreId()).orElseThrow();
    }
}
