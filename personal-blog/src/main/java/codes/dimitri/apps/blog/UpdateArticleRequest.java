package codes.dimitri.apps.blog;

import java.time.LocalDate;

public record UpdateArticleRequest(String title, LocalDate publishedAt, String content) {
}
