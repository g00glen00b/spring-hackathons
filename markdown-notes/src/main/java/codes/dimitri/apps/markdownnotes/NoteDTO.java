package codes.dimitri.apps.markdownnotes;

import java.util.UUID;

public record NoteDTO(UUID id, String filename) {
    public static NoteDTO from(Note note) {
        return new NoteDTO(note.id(), note.filename());
    }
}
