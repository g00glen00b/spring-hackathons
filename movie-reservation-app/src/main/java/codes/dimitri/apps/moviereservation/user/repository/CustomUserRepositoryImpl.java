package codes.dimitri.apps.moviereservation.user.repository;

import codes.dimitri.apps.moviereservation.user.UserId;

import java.util.UUID;

class CustomUserRepositoryImpl implements CustomUserRepository {
    @Override
    public UserId nextId() {
        return new UserId(UUID.randomUUID());
    }
}
