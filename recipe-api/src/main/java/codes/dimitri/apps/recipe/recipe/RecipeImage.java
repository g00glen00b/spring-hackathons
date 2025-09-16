package codes.dimitri.apps.recipe.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeImage {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @Setter
    private Recipe recipe;
    private String format;

    public RecipeImage(Recipe recipe, String format) {
        this(UUID.randomUUID(), recipe, format);
    }
}
