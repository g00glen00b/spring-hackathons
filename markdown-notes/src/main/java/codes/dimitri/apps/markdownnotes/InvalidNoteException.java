package codes.dimitri.apps.markdownnotes;

public class InvalidNoteException extends RuntimeException {
    public InvalidNoteException(String message, Throwable cause) {
        super(message, cause);
    }
}
