package codes.dimitri.apps.emojitranslator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.regex.Pattern;

@ConfigurationProperties(prefix = "emojitranslator")
public record EmojiTranslatorProperties(
    List<MappingResource> mappingResources,
    @DefaultValue("100") int chunkSize
) {
    public record MappingResource(Resource resource, Pattern pattern) {

    }
}
