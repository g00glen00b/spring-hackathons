package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, ShowtimeId>, CustomShowtimeRepository {
    List<Showtime> findAllByMovieIdAndStartingAtAfter(MovieId movieId, Instant startingAt);
    @Query("select s from Showtime s where s.theatreId = ?1 and s.startingAt <= ?3 and s.endingAt > ?2")
    List<Showtime> findAllByTheatreIdAndOverlap(TheatreId theatreId, Instant startingAt, Instant endingAt);
}
