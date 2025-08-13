package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteShowtime {
    private final ShowtimeRepository repository;

    public void execute(DeleteShowtimeParameters parameters) {
        repository.deleteById(parameters.showtimeId());
    }
}
