package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.ShowtimeId;

import java.util.UUID;

class CustomShowtimeRepositoryImpl implements CustomShowtimeRepository {
    @Override
    public ShowtimeId nextId() {
        return new ShowtimeId(UUID.randomUUID());
    }
}
