package codes.dimitri.apps.emojitranslator;

import codes.dimitri.apps.emojitranslator.web.EmojiTranslationResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = EmojiTranslatorApplicationTest.RestClientTestConfiguration.class)
class EmojiTranslatorApplicationTest {
    @Autowired
    private RestClient restClient;

    @ParameterizedTest
    @CsvSource({
        "let's get a coffee before our meeting,let's get a ☕ before our \uD83E\uDD1D\uD83D\uDDD3️",
        "let's order a taxi with our credit card,let's order a \uD83D\uDE95 with our \uD83D\uDCB3",
        "you need to wear sunglasses because the sun is shining, you need to wear \uD83D\uDD76️ because the ☀️ is shining"
    })
    void replacesEmoji(String text, String expected) {
        EmojiTranslationResponse result = restClient
            .get()
            .uri(builder -> builder
                .pathSegment("api", "emoji", "translation")
                .queryParam("text", text)
                .build())
            .retrieve()
            .body(EmojiTranslationResponse.class);
        assertThat(result).isEqualTo(new EmojiTranslationResponse(expected));
    }

    @Lazy
    @TestConfiguration
    static class RestClientTestConfiguration {
        @Bean
        RestClient emojiTranslatorRestClient(@Value("http://localhost:${local.server.port}") String address) {
            return RestClient.create(address);
        }
    }
}