package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Genre;
import codes.dimitri.apps.moviereservation.movie.repository.GenreRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListAllGenres {
    private final GenreRepository genreRepository;

    public List<Genre> execute() {
        return genreRepository.findAll();
    }
}
