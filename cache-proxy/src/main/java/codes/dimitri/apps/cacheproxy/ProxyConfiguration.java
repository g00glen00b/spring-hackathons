package codes.dimitri.apps.cacheproxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableCaching
@ConditionalOnWebApplication
class ProxyConfiguration {
    @Bean
    ProxyClient proxyClient(RestClient restClient, CacheManager cacheManager) {
        return new ProxyClient(restClient, cacheManager);
    }

    @Bean
    RestClient restClient(RestClient.Builder builder, @Value("${proxy.location}") String proxyLocation) {
        return builder
            .baseUrl(proxyLocation)
            .build();
    }
}
