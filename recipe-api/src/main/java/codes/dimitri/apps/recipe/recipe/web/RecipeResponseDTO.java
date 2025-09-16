package codes.dimitri.apps.recipe.recipe.web;

import codes.dimitri.apps.recipe.recipe.Recipe;
import codes.dimitri.apps.recipe.recipe.RecipeImage;

import java.util.List;
import java.util.UUID;

public record RecipeResponseDTO(UUID id, String title, String description, RecipeChefDTO chef, List<UUID> imageIds) {
    public static RecipeResponseDTO of(Recipe recipe) {
        return new RecipeResponseDTO(
            recipe.getId(),
            recipe.getTitle(),
            recipe.getDescription(),
            RecipeChefDTO.of(recipe.getChef()),
            recipe.getImages().stream().map(RecipeImage::getId).toList()
        );
    }
}
