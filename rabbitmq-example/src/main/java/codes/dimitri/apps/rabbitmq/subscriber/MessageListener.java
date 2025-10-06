package codes.dimitri.apps.rabbitmq.subscriber;

import codes.dimitri.apps.rabbitmq.app.RabbitMqConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
class MessageListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitMqConfiguration.QUEUE_NAME),
        exchange = @Exchange(value = RabbitMqConfiguration.TOPIC_EXCHANGE_NAME),
        key = RabbitMqConfiguration.ROUTING_KEY)
    )
    public void receiveMessage(@Payload String message) {
        log.info("⬅️ Received message: {}", message);
    }
}
