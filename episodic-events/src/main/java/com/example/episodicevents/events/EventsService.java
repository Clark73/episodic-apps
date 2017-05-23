package com.example.episodicevents.events;


import com.example.episodicevents.RabbitConfig;
import com.example.episodicevents.events.entity.Event;
import com.example.episodicevents.events.entity.EventsRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class EventsService {

    private final RabbitConfig rabbitConfig;
    private final RabbitTemplate rabbitTemplate;
    private final EventsRepository eventsRepository;

    public EventsService(RabbitConfig rabbitConfig, RabbitTemplate rabbitTemplate, EventsRepository eventsRepository) {
        this.rabbitConfig = rabbitConfig;
        this.rabbitTemplate = rabbitTemplate;
        this.eventsRepository = eventsRepository;
        assert rabbitConfig != null;
        assert rabbitTemplate != null;
        assert eventsRepository != null;
    }

    public void saveEvent (Event event) {
        event.publish(rabbitTemplate, rabbitConfig);
        eventsRepository.save(event);
    }

}
