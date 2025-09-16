package codes.dimitri.apps.recipe.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    @Query("select i from Recipe r inner join r.images i where i.id = ?1")
    Optional<RecipeImage> findImageById(UUID imageId);
}
