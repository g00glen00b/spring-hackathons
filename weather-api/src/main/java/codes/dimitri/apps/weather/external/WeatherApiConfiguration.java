package codes.dimitri.apps.weather.external;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(WeatherApiProperties.class)
class WeatherApiConfiguration {
    @Bean
    RestClient weatherApiRestClient(RestClient.Builder builder, WeatherApiProperties properties) {
        return builder
            .baseUrl(properties.baseUrl())
            .build();
    }

    @Bean
    WeatherApiClient weatherApiClient(RestClient weatherApiRestClient) {
        var adapter = RestClientAdapter.create(weatherApiRestClient);
        var factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(WeatherApiClient.class);
    }
}
