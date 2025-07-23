package codes.dimitri.apps.blog;

import java.time.LocalDate;

public record RenderedArticle(String title, LocalDate publishedAt, String renderedContent) {
}
