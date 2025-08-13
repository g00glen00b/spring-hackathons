package codes.dimitri.apps.moviereservation.reservation.repository;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import codes.dimitri.apps.moviereservation.reservation.Reservation;
import codes.dimitri.apps.moviereservation.reservation.ReservationId;
import codes.dimitri.apps.moviereservation.reservation.usecase.ReservationShowtimeTheatreTuple;
import codes.dimitri.apps.moviereservation.theatre.SeatId;
import codes.dimitri.apps.moviereservation.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId>, CustomReservationRepository {
    @Query("""
    select r as reservation, s as showtime, t as theatre
    from Reservation r, Showtime s, Theatre t
    where r.showtimeId = s.id
    and s.theatreId = t.id
    and s.startingAt >= ?2
    and r.ownerId = ?1
    order by s.startingAt
    """)
    List<ReservationShowtimeTheatreTuple> findAllByOwnerIdAndShowtimeStartingAtAfter(UserId ownerId, Instant date);
    @Query("""
    select distinct rs.id.seatId
    from Reservation r
    inner join r.reservedSeats rs
    where r.showtimeId = ?1
    """)
    List<SeatId> findAllReservedSeatIdsByShowtimeId(ShowtimeId showtimeId);
    @Query("""
    select case when count(rs) > 0 then true else false end
    from Reservation r
    inner join r.reservedSeats rs
    where r.showtimeId = ?1
    and rs.id.seatId in ?2
    """)
    boolean existsByShowtimeIdAndSeatId(ShowtimeId showtimeId, List<SeatId> seatIds);
}
