package codes.dimitri.apps.recipe.recipe.implementation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;

@ConfigurationProperties(prefix = "recipe")
public record RecipeProperties(
    @DefaultValue("file:./temp") Resource rootDirectory,
    @DefaultValue("200") int imageMaxWidth) {
    public Path rootDirectoryPath() throws IOException {
        return rootDirectory.getFile().toPath();
    }
}
