package com.brian.springreactivedog.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DOG_QUEUE = "dog.queue";
    public static final String DOG_EXCHANGE = "dog.exchange";
    public static final String DOG_ROUTING_KEY = "dog.routing.key";

    public static final String GENERAL_QUEUE = "general.queue";
    public static final String GENERAL_ROUTING_KEY = "events.#";

//    @Bean
//    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
//        var admin =  new RabbitAdmin(rabbitTemplate);
//        admin.declareExchange(new TopicExchange(DOG_EXCHANGE));
//        return admin;
//    }
    @Bean
    public Queue queue(){
        return new Queue(DOG_QUEUE);
    }

    @Bean
    public Queue generalQueue(){
        return new Queue(GENERAL_QUEUE);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(DOG_EXCHANGE);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(DOG_ROUTING_KEY);
    }
    @Bean
    public Binding generalBinding(){
        return BindingBuilder.bind(generalQueue())
                .to(exchange())
                .with(GENERAL_ROUTING_KEY);
    }
//
//    @Bean
//    public MessageConverter converter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate template(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }
}
