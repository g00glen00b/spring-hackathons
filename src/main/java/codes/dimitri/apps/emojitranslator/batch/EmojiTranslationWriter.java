package codes.dimitri.apps.emojitranslator.batch;

import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMapping;
import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMappingRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.stereotype.Component;

@Component
class EmojiTranslationWriter extends RepositoryItemWriter<EmojiTranslationMapping> {
    public EmojiTranslationWriter(EmojiTranslationMappingRepository repository) {
        setRepository(repository);
    }
}
