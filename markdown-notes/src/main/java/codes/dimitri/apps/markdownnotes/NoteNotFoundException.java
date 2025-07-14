package codes.dimitri.apps.markdownnotes;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {
    private final UUID id;

    public NoteNotFoundException(UUID id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Note with ID " + id + " not found.";
    }
}
