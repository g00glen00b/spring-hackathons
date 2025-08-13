package codes.dimitri.apps.moviereservation.movie.repository;

import codes.dimitri.apps.moviereservation.movie.MovieId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class MoviePosterRepository {
    private final Path directory;

    public Path save(MovieId id, MultipartFile file) {
        try {
            validateContentType(file);
            Path posterFile = directory.resolve(id.getId() + ".png");
            file.transferTo(posterFile);
            return posterFile;
        } catch (IOException e) {
            throw new MoviePosterException("Could not save poster file", e);
        }
    }

    public void deleteById(MovieId id) {
        try {
            Path posterFile = directory.resolve(id.getId() + ".png");
            Files.deleteIfExists(posterFile);
        } catch (IOException e) {
            throw new MoviePosterException("Could not delete poster file", e);
        }
    }

    public Resource findById(MovieId id) {
        Path posterFile = directory.resolve(id.getId() + ".png");
        return new FileSystemResource(posterFile);
    }

    private void validateContentType(MultipartFile file) {
        Assert.state(file.getContentType() != null || file.getOriginalFilename() != null, "poster must have a content type or an original filename");
        Assert.state(file.getContentType() == null || file.getContentType().equals("image/png"), "poster must be a PNG image");
        Assert.state(file.getOriginalFilename() == null || file.getOriginalFilename().endsWith(".png"), "poster must be a PNG image");
    }
}
