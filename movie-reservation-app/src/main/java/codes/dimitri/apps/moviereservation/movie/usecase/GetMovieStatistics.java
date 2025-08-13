package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class GetMovieStatistics {
    private final MovieRepository movieRepository;

    public MovieStatistics execute(GetMovieStatisticsParameters parameters) {
        return new MovieStatistics(
            movieRepository.countAllReservedSeatsByMovieId(parameters.movieId()),
            nullSafe(movieRepository.sumPricePerReservedSeatByMovieId(parameters.movieId()))
        );
    }

    private static BigDecimal nullSafe(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
