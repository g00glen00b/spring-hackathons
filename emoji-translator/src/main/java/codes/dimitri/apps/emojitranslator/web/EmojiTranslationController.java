package codes.dimitri.apps.emojitranslator.web;

import codes.dimitri.apps.emojitranslator.EmojiTranslator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emoji/translation")
class EmojiTranslationController {
    private final EmojiTranslator emojiTranslator;

    EmojiTranslationController(EmojiTranslator emojiTranslator) {
        this.emojiTranslator = emojiTranslator;
    }

    @GetMapping
    public EmojiTranslationResponse getTranslation(@RequestParam String text) {
        return new EmojiTranslationResponse(emojiTranslator.translate(text));
    }
}
