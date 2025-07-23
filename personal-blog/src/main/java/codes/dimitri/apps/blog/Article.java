package codes.dimitri.apps.blog;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate publishedAt;
    private String content;

    public Article() {
    }

    public Article(String title, LocalDate publishedAt, String content) {
        this.title = title;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Article empty() {
        return new Article("",  LocalDate.now(), "");
    }
}
