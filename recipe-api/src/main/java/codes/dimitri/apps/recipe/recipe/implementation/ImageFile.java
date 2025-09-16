package codes.dimitri.apps.recipe.recipe.implementation;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

public record ImageFile(MultipartFile file) {
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    public ImageFile {
        Assert.notNull(file, "file must not be null");
        Assert.state(file.getSize() > 0, "file must contain at least 1 byte");
    }

    public BufferedImage resize(int targetWidth) throws IOException {
        try (InputStream stream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(stream);
            Assert.notNull(image, "image must not be null");
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            int targetHeight = (int) (originalHeight * ((double) targetWidth / originalWidth));
            Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            return resizedImage;
        }
    }

    public String extension() {
        return getFileExtensionFromInputFile()
            .or(this::getFileExtensionFromDetection)
            .orElseThrow();
    }

    private Optional<String> getFileExtensionFromInputFile() {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(FILE_EXTENSION_SEPARATOR)) {
            return Optional.of(fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1));
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> getFileExtensionFromDetection() {
        try (InputStream stream = file.getInputStream()) {
            try (ImageInputStream imageStream = ImageIO.createImageInputStream(stream)) {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
                if (readers.hasNext()) {
                    return Optional.of(readers.next().getFormatName());
                }
                return Optional.empty();
            }
        } catch (IOException e) {
            return Optional.empty();
        }

    }
}
