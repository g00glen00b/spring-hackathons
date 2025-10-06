package codes.dimitri.apps.rabbitmq.publisher;

import codes.dimitri.apps.rabbitmq.app.RabbitMqConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
class MessageController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RabbitTemplate rabbitTemplate;

    MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestParam String message) {
        log.info("➡️ Sending message: {}", message);
        rabbitTemplate.convertAndSend(
            RabbitMqConfiguration.TOPIC_EXCHANGE_NAME,
            RabbitMqConfiguration.ROUTING_KEY,
            message
        );
    }
}
