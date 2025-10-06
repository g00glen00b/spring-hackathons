package codes.dimitri.apps.rabbitmq.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("publisher")
@ComponentScan("codes.dimitri.apps.rabbitmq.publisher")
class PublisherConfiguration {
}
