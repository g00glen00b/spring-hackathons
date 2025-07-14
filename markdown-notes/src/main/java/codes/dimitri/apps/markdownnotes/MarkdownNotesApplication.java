package codes.dimitri.apps.markdownnotes;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MarkdownNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarkdownNotesApplication.class, args);
	}

	@Bean
	public NoteManager notesManager(
		NoteProperties properties,
		Parser markdownParser,
		HtmlRenderer htmlRenderer,
		PlainTextRenderer plainTextRenderer,
		JLanguageTool languageTool
	) throws IOException {
		return new NoteManager(
			properties.rootDirectory().getFile().toPath(),
			markdownParser,
			htmlRenderer,
			plainTextRenderer,
			languageTool
		);
	}

	@Bean
	public HtmlRenderer htmlRenderer() {
		return HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();
	}

	@Bean
	public PlainTextRenderer plainTextRenderer() {
		return new PlainTextRenderer();
	}

	@Bean
	public Parser markdownParser() {
		return Parser.builder().extensions(List.of(TablesExtension.create())).build();
	}

	@Bean
	public JLanguageTool languageTool() {
		return new JLanguageTool(Languages.getLanguageForShortCode("en-GB"));
	}
}
