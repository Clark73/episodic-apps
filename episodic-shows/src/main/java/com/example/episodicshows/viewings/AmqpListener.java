package com.example.episodicshows.viewings;

import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.model.RabbitViewing;
import com.example.episodicshows.viewings.service.ViewingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.sound.InvalidDataException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Map;

@Configuration
public class AmqpListener implements RabbitListenerConfigurer {

    private final ViewingsService viewingsService;
    private final ObjectMapper objectMapper;

    public AmqpListener(ViewingsService viewingsService, @Qualifier("_halObjectMapper") ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.viewingsService = viewingsService;
        assert objectMapper != null;
        assert viewingsService != null;
    }

    @RabbitListener(queues = "episodic-progress")
    public void receiveMessage(RabbitViewing message) {
        System.out.println("************************************************");
        System.out.println(message.toString());
        System.out.println("************************************************");

        try {
            viewingsService.updateOrCreateViewing(new Viewing(
                    message.getUserId(),
                    message.getEpisodeId(),
                    message.getCreatedAt(),
                    message.getOffset()
            ));
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter mjmc = new MappingJackson2MessageConverter();
        mjmc.setObjectMapper(objectMapper);
        factory.setMessageConverter(mjmc);
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}