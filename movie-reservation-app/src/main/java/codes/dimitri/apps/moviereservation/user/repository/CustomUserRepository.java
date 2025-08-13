package codes.dimitri.apps.moviereservation.user.repository;

import codes.dimitri.apps.moviereservation.user.UserId;

public interface CustomUserRepository {
    UserId nextId();
}
