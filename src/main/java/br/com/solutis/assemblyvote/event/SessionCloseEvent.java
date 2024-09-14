package br.com.solutis.assemblyvote.event;

import br.com.solutis.assemblyvote.config.RabbitMQConfig;
import br.com.solutis.assemblyvote.to.SessionTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SessionCloseEvent {


    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SessionCloseEvent(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(SessionTO sessionTO) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, objectMapper.writeValueAsString(sessionTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
            //TODO: remover exeção e trabalhar com log4j
        }
    }
}
