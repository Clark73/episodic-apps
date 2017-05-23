package com.example.episodicevents.events;

import com.example.episodicevents.RabbitConfig;
import com.example.episodicevents.events.entity.Event;
import com.example.episodicevents.events.entity.EventsRepository;
import com.example.episodicevents.events.entity.ProgressEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EventsServiceTest {

    @Mock
    private RabbitConfig rabbitConfig;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private EventsRepository eventsRepository;

    private EventsService eventsService;

    @Before
    public void setup() {
        eventsService = new EventsService(rabbitConfig, rabbitTemplate, eventsRepository);
        when(rabbitConfig.getExchange()).thenReturn("foo");
        when(rabbitConfig.getKey()).thenReturn("bar");
    }

    @Test
    public void testPublishToQueue () {
        ProgressEvent event = new ProgressEvent();
        event.setEpisodeId(1l);
        event.setCreatedAt(LocalDateTime.now());
        event.setUserId(1l);
        event.setData(new ProgressEvent.Data());
        event.getData().setOffset(1);
        when(eventsRepository.save(event)).thenReturn(event);
        when(rabbitTemplate.convertSendAndReceive(anyString(), anyString(), any(Event.class))).thenReturn(null);


        eventsService.saveEvent(event);


    }


}
