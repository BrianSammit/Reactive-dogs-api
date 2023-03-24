package com.brian.springreactivedog.publisher;

import com.brian.springreactivedog.config.RabbitMQConfig;
import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class DogPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public DogPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(String dogWlkId, DogDTO dogDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new DogEvent(dogWlkId, dogDTO, "DogAdded"));
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DOG_EXCHANGE,
                RabbitMQConfig.DOG_ROUTING_KEY,
                message
        );
    }

    public void publishRemove(String dogWlkId, DogDTO dogDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new DogEvent(dogWlkId, dogDTO, "DogRemove"));
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DOG_EXCHANGE,
                RabbitMQConfig.DOG_ROUTING_KEY,
                message
        );
    }

    public void publishError(Throwable errorEvent){
    }
}
