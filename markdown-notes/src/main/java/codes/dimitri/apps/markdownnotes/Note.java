package codes.dimitri.apps.markdownnotes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public record Note(UUID id, Path file, NoteManager manager) {
    public String readContent() {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InvalidNoteException("Could not read note file", e);
        }
    }

    public String html() {
        return manager.html(this);
    }

    public String plainText() {
        return manager.plainText(this);
    }

    public String filename() {
        return file.getFileName().toString();
    }

    public List<NoteGrammarError> grammarErrors() {
        return manager.grammarErrors(this);
    }
}
