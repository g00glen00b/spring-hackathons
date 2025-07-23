package codes.dimitri.apps.blog;

import java.time.LocalDate;

public record CreateArticleRequest(String title, LocalDate publishedAt, String content) {
}
