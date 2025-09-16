package codes.dimitri.apps.recipe.recipe.web;

import codes.dimitri.apps.recipe.chef.Chef;

import java.util.UUID;

public record RecipeChefDTO(UUID id, String displayname) {
    public static RecipeChefDTO of(Chef chef) {
        return new RecipeChefDTO(chef.getId(), chef.getDisplayname());
    }
}
