package com.example.episodicevents.events.model;


import java.time.LocalDateTime;

public class ProgressQueueEvent {
    private long episodeId;
    private long userId;
    private LocalDateTime createdAt;
    private int offset;

    public ProgressQueueEvent(long episodeId, long userId, LocalDateTime createdAt, int offset) {
        this.episodeId = episodeId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.offset = offset;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getOffset() {
        return offset;
    }


}
