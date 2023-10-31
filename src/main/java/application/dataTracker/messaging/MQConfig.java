package application.dataTracker.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {

    public static final String QUEUE = "message-queue";

    public Queue queue() {
        return new Queue(QUEUE);
    }
}
