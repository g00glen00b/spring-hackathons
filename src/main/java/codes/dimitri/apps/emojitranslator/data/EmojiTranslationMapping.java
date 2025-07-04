package codes.dimitri.apps.emojitranslator.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.regex.Pattern;

@Entity
public class EmojiTranslationMapping {
    public static final String WORD_BOUNDARY = "\\b";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mapping;
    private String emoji;

    public EmojiTranslationMapping() {
    }

    public EmojiTranslationMapping(String mapping, String emoji) {
        this.mapping = mapping;
        this.emoji = emoji;
    }

    public String translate(String text) {
        return text.replaceAll(WORD_BOUNDARY + Pattern.quote(mapping) + WORD_BOUNDARY, emoji);
    }
}
