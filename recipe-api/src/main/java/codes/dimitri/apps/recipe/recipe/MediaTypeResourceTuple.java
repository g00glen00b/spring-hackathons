package codes.dimitri.apps.recipe.recipe;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record MediaTypeResourceTuple(Resource resource, MediaType mediaType) {
}
