package codes.dimitri.apps.cacheproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ConditionalOnNotWebApplication
public class CacheClearConfiguration {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    RestClient restClient(RestClient.Builder builder, @Value("${proxy.port:8080}") int port) {
        return  builder
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Bean
    ApplicationRunner cacheClearRunner(RestClient restClient) {
        return args -> {
            restClient
                .delete()
                .uri("/cache")
                .retrieve()
                .toBodilessEntity();
            log.info("🗑️ Cache cleared");
        };
    }
}
