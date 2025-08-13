package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import codes.dimitri.apps.moviereservation.movie.repository.MovieSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class FilterMovies {
    private final MovieRepository movieRepository;

    public Page<Movie> execute(FilterMoviesParameters parameters) {
        Specification<Movie> specification = mapToSpecification(parameters);
        var pageRequest = PageRequest.of(parameters.page(), parameters.size(), Sort.by("title"));
        return movieRepository.findAll(specification, pageRequest);
    }

    private Specification<Movie> mapToSpecification(FilterMoviesParameters parameters) {
        return Specification.anyOf(
            MovieSpecifications.withGenreId(parameters.genreId())
        );
    }
}
