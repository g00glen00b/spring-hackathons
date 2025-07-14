package codes.dimitri.apps.markdownnotes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "note")
public record NoteProperties(@DefaultValue("file:./temp") Resource rootDirectory) {
}
