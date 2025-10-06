package codes.dimitri.apps.rabbitmq.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("subscriber")
@ComponentScan("codes.dimitri.apps.rabbitmq.subscriber")
class SubscriberConfiguration {
}
