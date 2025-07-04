package codes.dimitri.apps.emojitranslator.batch;

import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMapping;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.file.LineMapper;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PatternLineMapper implements LineMapper<Optional<EmojiTranslationMapping>> {
    private static final int TEXT_GROUP = 2;
    private static final int EMOJI_GROUP = 1;
    private static final int EXPECTED_GROUP_COUNT = 2;
    private static final String LINE_COMMENT_SYMBOL = "#";
    private final Pattern pattern;

    PatternLineMapper(Pattern pattern) {
        this.pattern = pattern;
    }

    @Nonnull
    @Override
    public Optional<EmojiTranslationMapping> mapLine(@Nonnull String line, int lineNumber) {
        if (isComment(line) || line.isBlank()) return Optional.empty();
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches() || !hasEnoughGroupMatches(matcher)) return Optional.empty();
        return Optional.of(new EmojiTranslationMapping(matcher.group(TEXT_GROUP), matcher.group(EMOJI_GROUP)));
    }

    private static boolean hasEnoughGroupMatches(Matcher matcher) {
        return matcher.groupCount() == EXPECTED_GROUP_COUNT;
    }

    private static boolean isComment(String line) {
        return line.startsWith(LINE_COMMENT_SYMBOL);
    }
}
