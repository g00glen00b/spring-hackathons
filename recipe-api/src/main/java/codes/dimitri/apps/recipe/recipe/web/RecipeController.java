package codes.dimitri.apps.recipe.recipe.web;

import codes.dimitri.apps.recipe.recipe.Recipe;
import codes.dimitri.apps.recipe.recipe.usecase.CreateRecipe;
import codes.dimitri.apps.recipe.recipe.usecase.GetRecipeImage;
import codes.dimitri.apps.recipe.recipe.usecase.ListRecipes;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final ListRecipes listRecipes;
    private final CreateRecipe createRecipe;
    private final GetRecipeImage getRecipeImage;

    @GetMapping
    public Page<RecipeResponseDTO> listRecipes(Pageable pageable) {
        var parameters = new ListRecipes.Parameters(pageable);
        return listRecipes.execute(parameters).map(RecipeResponseDTO::of);
    }

    @PostMapping
    public RecipeResponseDTO createRecipe(
        @RequestPart CreateRecipeRequestDTO request,
        @RequestPart("image") List<MultipartFile> images,
        @AuthenticationPrincipal DecodedJWT jwt) {
        var chefId = UUID.fromString(jwt.getSubject());
        CreateRecipe.Parameters parameters = request.toParameters(chefId, images);
        Recipe recipe = createRecipe.execute(parameters);
        return RecipeResponseDTO.of(recipe);
    }

    @GetMapping("/image/{imageId}")
    public Resource getImage(@PathVariable UUID imageId) {
        var parameters = new GetRecipeImage.Parameters(imageId);
        return getRecipeImage.execute(parameters);
    }
}
