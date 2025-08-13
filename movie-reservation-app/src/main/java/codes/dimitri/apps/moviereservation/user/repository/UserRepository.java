package codes.dimitri.apps.moviereservation.user.repository;

import codes.dimitri.apps.moviereservation.user.User;
import codes.dimitri.apps.moviereservation.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId>, CustomUserRepository {
    Optional<User> findByUsername(String username);
}
