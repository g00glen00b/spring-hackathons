package codes.dimitri.apps.weather.web;

import io.github.bucket4j.Bucket;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RatelimitingProperties.class)
class RatelimitingConfiguration {
    @Bean
    Bucket bucket(RatelimitingProperties properties) {
        return Bucket.builder()
            .addLimit(limit -> limit
                .capacity(properties.capacity())
                .refillGreedy(properties.capacity(), properties.period()))
            .build();
    }
}
