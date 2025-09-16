package codes.dimitri.apps.recipe.recipe.implementation;

import codes.dimitri.apps.recipe.recipe.Recipe;
import codes.dimitri.apps.recipe.recipe.RecipeImage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
@RequiredArgsConstructor
public class RecipeImageFileRepository {
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private final RecipeProperties properties;

    @SneakyThrows
    public RecipeImage save(Recipe recipe, ImageFile file) {
        String format = file.extension();
        var recipeImage = new RecipeImage(recipe, format);
        Path filePath = getRecipeImagePath(recipeImage);
        BufferedImage resizedImage = file.resize(properties.imageMaxWidth());
        try (OutputStream stream  = Files.newOutputStream(filePath)) {
            ImageIO.write(resizedImage, format, stream);
        }
        return recipe.addImage(recipeImage);
    }

    private Path getRecipeImagePath(RecipeImage recipeImage) throws IOException {
        Path recipeDirectory = createRecipeDirectory(recipeImage.getRecipe());
        return recipeDirectory.resolve(recipeImage.getId().toString() + FILE_EXTENSION_SEPARATOR + recipeImage.getFormat());
    }

    @SneakyThrows
    public Resource findById(RecipeImage recipeImage) {
        Path filePath = getRecipeImagePath(recipeImage);
        return new FileSystemResource(filePath);
    }

    private Path createRecipeDirectory(Recipe recipe) throws IOException {
        Path recipeDirectory = properties.rootDirectoryPath().resolve(recipe.getId().toString());
        if (!Files.exists(recipeDirectory)) {
            Files.createDirectories(recipeDirectory);
        }
        return recipeDirectory;
    }
}
