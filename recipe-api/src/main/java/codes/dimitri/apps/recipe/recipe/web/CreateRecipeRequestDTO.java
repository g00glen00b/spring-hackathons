package codes.dimitri.apps.recipe.recipe.web;

import codes.dimitri.apps.recipe.recipe.usecase.CreateRecipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record CreateRecipeRequestDTO(String title, String description) {
    public CreateRecipe.Parameters toParameters(UUID chefId, List<MultipartFile> images) {
        return new CreateRecipe.Parameters(chefId, title, description, images);
    }
}
