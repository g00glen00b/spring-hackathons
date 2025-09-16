package codes.dimitri.apps.recipe.chef;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChefRepository extends JpaRepository<Chef, UUID> {
    Optional<Chef> findByUsername(String username);
    boolean existsByUsername(String username);
}
