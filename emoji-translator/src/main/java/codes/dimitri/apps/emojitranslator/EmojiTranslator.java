package codes.dimitri.apps.emojitranslator;

import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMappingRepository;
import org.springframework.stereotype.Component;

@Component
public class EmojiTranslator {
    private final EmojiTranslationMappingRepository repository;

    public EmojiTranslator(EmojiTranslationMappingRepository repository) {
        this.repository = repository;
    }

    public String translate(String text) {
        return repository.findAll().stream().reduce(
            text,
            (current, translation) -> translation.translate(current),
            (a, b) -> a
        );
    }
}
