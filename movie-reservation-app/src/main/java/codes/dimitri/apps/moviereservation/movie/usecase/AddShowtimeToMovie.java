package codes.dimitri.apps.moviereservation.movie.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.repository.MovieRepository;
import codes.dimitri.apps.moviereservation.movie.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class AddShowtimeToMovie {
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    public Showtime execute(AddShowtimeToMovieParameters parameters) {
        Movie movie = movieRepository.findById(parameters.movieId()).orElseThrow();
        Instant endingAt = parameters.startingAt().plus(movie.getDuration());
        validateNoOverlapInTheatre(parameters, endingAt);
        Showtime showtime = new Showtime(
            showtimeRepository.nextId(),
            movie,
            parameters.theatreId(),
            parameters.startingAt(),
            endingAt,
            parameters.price()
        );
        movie.getShowtimes().add(showtime);
        return showtime;
    }

    private void validateNoOverlapInTheatre(AddShowtimeToMovieParameters parameters, Instant endingAt) {
        List<Showtime> overlappingShowtimes = showtimeRepository.findAllByTheatreIdAndOverlap(parameters.theatreId(), parameters.startingAt(), endingAt);
        String overlappingMovieTitles = overlappingShowtimes
            .stream()
            .map(Showtime::getMovie)
            .map(Movie::getTitle)
            .collect(Collectors.joining(", "));
        Assert.state(overlappingShowtimes.isEmpty(), overlappingMovieTitles + " are already playing at the same time in this theatre");
    }
}
