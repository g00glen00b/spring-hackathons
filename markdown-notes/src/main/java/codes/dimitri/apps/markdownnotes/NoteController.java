package codes.dimitri.apps.markdownnotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NoteManager noteManager;

    public NoteController(NoteManager noteManager) {
        this.noteManager = noteManager;
    }

    @PostMapping
    public NoteDTO upload(@RequestParam("file") MultipartFile file) {
        return NoteDTO.from(noteManager.upload(file));
    }

    @GetMapping
    public List<NoteDTO> getNotes() {
        return noteManager.list().stream().map(NoteDTO::from).toList();
    }

    @GetMapping(value = "/{id}/render", produces = MediaType.TEXT_HTML_VALUE)
    public String renderNote(@PathVariable UUID id) {
        return noteManager.get(id).html();
    }

    @GetMapping("/{id}/grammar-error")
    public List<NoteGrammarError> getGrammarError(@PathVariable UUID id) {
        return noteManager.get(id).grammarErrors();
    }

    @ExceptionHandler(InvalidNoteException.class)
    public ProblemDetail handleInvalidNoteException(InvalidNoteException ex) {
        logger.warn("Invalid note", ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problem.setTitle("Invalid note");
        problem.setType(URI.create("https://markdown-notes-app/errors/invalid-note"));
        return problem;
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ProblemDetail hanldeNoteNotFoundException(NoteNotFoundException ex) {
        logger.warn("Note not found", ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Note not found");
        problem.setType(URI.create("https://markdown-notes-app/errors/note-not-found"));
        return problem;
    }
}
