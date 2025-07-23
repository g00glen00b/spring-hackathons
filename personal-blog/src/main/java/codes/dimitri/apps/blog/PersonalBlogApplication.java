package codes.dimitri.apps.blog;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PersonalBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogApplication.class, args);
	}

	@Bean
	public HtmlRenderer htmlRenderer() {
		return HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();
	}

	@Bean
	public Parser markdownParser() {
		return Parser.builder().extensions(List.of(TablesExtension.create())).build();
	}
}
