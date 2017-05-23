package com.example.episodicevents.events.entity;

import com.example.episodicevents.RabbitConfig;
import com.fasterxml.jackson.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayEvent.class, name = "play"),
        @JsonSubTypes.Type(value = PauseEvent.class, name = "pause"),
        @JsonSubTypes.Type(value = FastForwardEvent.class, name = "fastForward"),
        @JsonSubTypes.Type(value = RewindEvent.class, name = "rewind"),
        @JsonSubTypes.Type(value = ProgressEvent.class, name = "progress"),
        @JsonSubTypes.Type(value = ScrubEvent.class, name = "scrub")

})
public class Event {
    @Id
    private String id;
    private long userId;
    private long showId;
    private long episodeId;
    private LocalDateTime createdAt;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public long getShowId() {
        return showId;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public String getCreatedAtString() {
        return createdAt.toString();
    }

    public String getEventType() {
        throw  new UnsupportedOperationException();
    }

    public void publish(RabbitTemplate rabbitTemplate, RabbitConfig rabbitConfig) {}
}
