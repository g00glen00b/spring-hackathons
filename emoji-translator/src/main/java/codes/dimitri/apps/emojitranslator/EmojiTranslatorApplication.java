package codes.dimitri.apps.emojitranslator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EmojiTranslatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmojiTranslatorApplication.class, args);
	}

}
