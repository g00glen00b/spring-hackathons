package codes.dimitri.apps.blog;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BlogController {
    private final ArticleRepository repository;
    private final HtmlRenderer renderer;
    private final Parser parser;

    public BlogController(ArticleRepository repository, HtmlRenderer renderer, Parser parser) {
        this.repository = repository;
        this.renderer = renderer;
        this.parser = parser;
    }

    @GetMapping
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(ModelMap model) {
        model.put("articles", repository.findAll());
        model.put("isAdmin", false);
        return "home";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable int id, ModelMap model) {
        Article article = repository.findById(id).orElseThrow();
        Node parsedContent = parser.parse(article.getContent());
        String renderedContent = renderer.render(parsedContent);
        RenderedArticle renderedArticle = new RenderedArticle(
            article.getTitle(),
            article.getPublishedAt(),
            renderedContent
        );
        model.put("isAdmin", false);
        model.put("article", renderedArticle);
        return "view";
    }
}
