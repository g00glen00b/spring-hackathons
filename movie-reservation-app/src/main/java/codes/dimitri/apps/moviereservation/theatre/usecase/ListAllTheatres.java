package codes.dimitri.apps.moviereservation.theatre.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.theatre.Theatre;
import codes.dimitri.apps.moviereservation.theatre.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListAllTheatres {
    private final TheatreRepository theatreRepository;

    public List<Theatre> execute() {
        return theatreRepository.findAll();
    }
}
