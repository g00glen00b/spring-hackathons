package codes.dimitri.apps.emojitranslator.batch;

import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMapping;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmojiTranslationMappingProcessor implements ItemProcessor<Optional<EmojiTranslationMapping>, EmojiTranslationMapping> {
    @Override
    public EmojiTranslationMapping process(Optional<EmojiTranslationMapping> item) {
        return item.orElse(null);
    }
}
