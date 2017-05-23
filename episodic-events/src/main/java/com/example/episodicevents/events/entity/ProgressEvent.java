package com.example.episodicevents.events.entity;


import com.example.episodicevents.RabbitConfig;
import com.example.episodicevents.events.model.ProgressQueueEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ProgressEvent extends Event {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JsonProperty("type")
    @Override
    public String getEventType() {
        return "progress";
    }

    @Override
    public void publish(RabbitTemplate rabbitTemplate, RabbitConfig rabbitConfig) {
        rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getKey(), new ProgressQueueEvent(
                this.getEpisodeId(),
                this.getUserId(),
                this.getCreatedAt(),
                this.getData().getOffset()
        ));

    }

    static public class Data {
        private int offset;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offest) {
            this.offset = offest;
        }
    }
}
