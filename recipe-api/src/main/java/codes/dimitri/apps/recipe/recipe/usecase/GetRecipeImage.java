package codes.dimitri.apps.recipe.recipe.usecase;

import codes.dimitri.apps.recipe.UseCase;
import codes.dimitri.apps.recipe.recipe.RecipeImage;
import codes.dimitri.apps.recipe.recipe.RecipeRepository;
import codes.dimitri.apps.recipe.recipe.implementation.RecipeImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class GetRecipeImage {
    private final RecipeRepository recipeRepository;
    private final RecipeImageFileRepository recipeImageFileRepository;

    public record Parameters(UUID imageId) {
        public Parameters {
            Assert.notNull(imageId, "imageId must not be null");
        }
    }

    public Resource execute(Parameters parameters) {
        RecipeImage image = recipeRepository.findImageById(parameters.imageId).orElseThrow();
        return recipeImageFileRepository.findById(image);
    }
}
