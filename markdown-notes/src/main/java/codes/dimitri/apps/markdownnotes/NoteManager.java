package codes.dimitri.apps.markdownnotes;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jetbrains.annotations.NotNull;
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class NoteManager {
    private final Path rootDirectory;
    private final Parser markdownParser;
    private final HtmlRenderer htmlRenderer;
    private final PlainTextRenderer plainTextRenderer;
    private final JLanguageTool languageTool;

    public NoteManager(Path rootDirectory, Parser markdownParser, HtmlRenderer htmlRenderer, PlainTextRenderer plainTextRenderer, JLanguageTool languageTool) {
        this.rootDirectory = rootDirectory;
        this.markdownParser = markdownParser;
        this.htmlRenderer = htmlRenderer;
        this.plainTextRenderer = plainTextRenderer;
        this.languageTool = languageTool;
    }

    public Note upload(MultipartFile file) {
        UUID id = UUID.randomUUID();
        try {
            Path noteDir = createNoteDirectory(id);
            Path noteFile = createNotFile(file, noteDir);
            file.transferTo(noteFile);
            return new Note(id, noteFile, this);
        } catch (IOException e) {
            throw new InvalidNoteException("Failed to upload note file", e);
        }
    }

    @NotNull
    private static Path createNotFile(MultipartFile file, Path noteDir) {
        String filename = file.getOriginalFilename() == null ? "note.md" : file.getOriginalFilename();
        return noteDir.resolve(filename);
    }

    @NotNull
    private Path createNoteDirectory(UUID id) throws IOException {
        Path noteDir = rootDirectory.resolve(id.toString());
        Files.createDirectories(noteDir);
        return noteDir;
    }

    public List<Note> list() {
        try (Stream<Path> dirs = Files.list(rootDirectory)) {
            return dirs
                .filter(Files::isDirectory)
                .map(this::getNote)
                .toList();
        } catch (IOException e) {
            throw new InvalidNoteException("Failed to list notes from directory", e);
        }
    }

    public Note get(UUID id) {
        Path noteDir = rootDirectory.resolve(id.toString());
        if (!Files.isDirectory(noteDir)) throw new NoteNotFoundException(id);
        return getNote(noteDir);
    }

    public String html(Note note) {
        Node node = markdownParser.parse(note.readContent());
        return htmlRenderer.render(node);
    }

    public String plainText(Note note) {
        Node node = markdownParser.parse(note.readContent());
        return plainTextRenderer.render(node);
    }

    public List<NoteGrammarError> grammarErrors(Note note) {
        try {
            List<RuleMatch> matches = languageTool.check(note.plainText());
            return matches.stream().map(NoteGrammarError::from).toList();
        } catch (IOException e) {
            throw new InvalidNoteException("Failed to check grammar", e);
        }
    }

    private Note getNote(Path dir) {
        try (Stream<Path> files = Files.list(dir)) {
            UUID id = UUID.fromString(dir.getFileName().toString());
            return files
                .filter(Files::isRegularFile)
                .filter(NoteManager::isMarkdownFile)
                .findFirst()
                .map(markdownFile -> new Note(id, markdownFile, this))
                .orElseThrow(() -> new NoteNotFoundException(id));
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidNoteException("Failed to retrieve note", e);
        }
    }

    private static boolean isMarkdownFile(Path file) {
        return file.getFileName().toString().endsWith(".md");
    }
}
