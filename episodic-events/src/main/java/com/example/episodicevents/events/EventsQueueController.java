package com.example.episodicevents.events;


import com.example.episodicevents.RabbitConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsQueueController {

    private final RabbitConfig rabbitConfig;
    private final ObjectMapper mapper;

    public EventsQueueController(RabbitConfig rabbitConfig, @Qualifier("_halObjectMapper") ObjectMapper mapper) {
        this.rabbitConfig = rabbitConfig;
        this.mapper = mapper;
        assert rabbitConfig != null;
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(rabbitConfig.getExchange());
    }

    @Bean
    public Queue queue() {
        return new Queue("episodic-progress");
    }

    @Bean
    public Binding declareBinding() {
        return BindingBuilder.bind(queue()).to(appExchange()).with(rabbitConfig.getKey());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(mapper);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}