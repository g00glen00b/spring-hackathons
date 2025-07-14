package codes.dimitri.apps.markdownnotes;

import org.languagetool.rules.RuleMatch;

public record NoteGrammarError(String message, String sentence, String suggestion) {
    public static NoteGrammarError from(RuleMatch match) {
        return new NoteGrammarError(
            match.getMessage(),
            match.getSentence().getText(),
            match.getSuggestedReplacements().isEmpty() ? null : match.getSuggestedReplacements().getFirst()
        );
    }
}
