package codes.dimitri.apps.blog;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private final ArticleRepository repository;

    public AdminController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(ModelMap model) {
        model.addAttribute("articles", repository.findAll());
        model.put("isAdmin", true);
        return "admin";
    }

    @GetMapping("/new")
    public String newArticle(ModelMap model) {
        model.addAttribute("article", Article.empty());
        model.addAttribute("isAdmin", true);
        model.addAttribute("action", "Publish");
        model.addAttribute("title", "New Article");
        model.addAttribute("location", "/new");
        return "article";
    }

    @PostMapping("/new")
    public String postNewArticle(@ModelAttribute CreateArticleRequest article) {
        repository.save(new Article(article.title(), article.publishedAt(), article.content()));
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editArticle(@PathVariable int id, ModelMap model) {
        Article article = repository.findById(id).orElseThrow();
        model.addAttribute("article", article);
        model.addAttribute("isAdmin", true);
        model.addAttribute("action", "Update");
        model.addAttribute("title", "Update Article");
        model.addAttribute("location", "/edit/" + id);
        return "article";
    }

    @Transactional
    @PostMapping("/edit/{id}")
    public String editArticle(@PathVariable int id, @ModelAttribute UpdateArticleRequest article, ModelMap model) {
        Article originalArticle = repository.findById(id).orElseThrow();
        originalArticle.setTitle(article.title());
        originalArticle.setPublishedAt(article.publishedAt());
        originalArticle.setContent(article.content());
        return "redirect:/admin";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/admin";
    }
}
