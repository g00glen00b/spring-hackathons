package codes.dimitri.apps.moviereservation.theatre.repository;

import codes.dimitri.apps.moviereservation.theatre.Theatre;
import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, TheatreId> {
}
