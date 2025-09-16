package codes.dimitri.apps.recipe.recipe.usecase;

import codes.dimitri.apps.recipe.UseCase;
import codes.dimitri.apps.recipe.chef.Chef;
import codes.dimitri.apps.recipe.chef.ChefRepository;
import codes.dimitri.apps.recipe.recipe.Recipe;
import codes.dimitri.apps.recipe.recipe.RecipeRepository;
import codes.dimitri.apps.recipe.recipe.implementation.ImageFile;
import codes.dimitri.apps.recipe.recipe.implementation.RecipeImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@UseCase(readOnly = false)
@RequiredArgsConstructor
public class CreateRecipe {
    private final RecipeRepository recipeRepository;
    private final ChefRepository chefRepository;
    private final RecipeImageFileRepository imageFileRepository;

    public record Parameters(UUID chefId, String title, String description, List<MultipartFile> images) {
        public Parameters {
            Assert.notNull(chefId, "chefId must not be null");
            Assert.notNull(title, "title must not be null");
            Assert.notNull(description, "description must not be null");
            Assert.notNull(images, "images must not be null");
            Assert.state(title.length() <= 128, "title must be less than 128 characters");
            Assert.state(description.length() <= 512, "description must be less than 512 characters");
        }
    }

    public Recipe execute(Parameters parameters) {
        Chef chef = chefRepository.findById(parameters.chefId).orElseThrow();
        var recipe = new Recipe(chef, parameters.title, parameters.description);
        var savedRecipe = recipeRepository.save(recipe);
        parameters.images
            .stream()
            .map(ImageFile::new)
            .forEach(imageFile -> imageFileRepository.save(savedRecipe, imageFile));
        return savedRecipe;
    }
}
