package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;

public interface CustomShowtimeRepository {
    ShowtimeId nextId();
}
