package codes.dimitri.apps.recipe.recipe.usecase;

import codes.dimitri.apps.recipe.UseCase;
import codes.dimitri.apps.recipe.recipe.Recipe;
import codes.dimitri.apps.recipe.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

@UseCase
@RequiredArgsConstructor
public class ListRecipes {
    private final RecipeRepository repository;

    public record Parameters(Pageable pageable) {
        public Parameters {
            Assert.notNull(pageable, "pageable must not be null");
        }
    }

    public Page<Recipe> execute(Parameters parameters) {
        return repository.findAll(parameters.pageable());
    }
}
