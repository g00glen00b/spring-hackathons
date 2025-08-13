package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.MovieId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface MovieRepository extends JpaRepository<Movie, MovieId>, CustomMovieRepository, JpaSpecificationExecutor<Movie> {
    @Query("""
    select count(distinct rs.id)
    from ReservationSeat rs
    inner join rs.reservation r
    inner join Showtime s on r.showtimeId = s.id
    inner join s.movie m
    where m.id = ?1
    """)
    int countAllReservedSeatsByMovieId(MovieId movieId);

    @Query("""
    select sum(s.price)
    from ReservationSeat rs
    inner join rs.reservation r
    inner join Showtime s on r.showtimeId = s.id
    inner join s.movie m
    where m.id = ?1
    """)
    BigDecimal sumPricePerReservedSeatByMovieId(MovieId movieId);
}
