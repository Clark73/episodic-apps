package com.example.episodicshows.viewings.model;

import java.time.LocalDateTime;

/**
 *         {
 *            "userId": 12,
 *            "episodeId": 456,
 *            "createdAt": "2017-11-08T15:59:13.0091745",
 *            "offset": 4
 *        }
 */
public class RabbitViewing {
    private long userId;
    private long episodeId;
    private LocalDateTime createdAt;
    private int offset;

    public RabbitViewing() {
    }

    public RabbitViewing(long userId, long episodeId, LocalDateTime createdAt, int offset) {
        this.userId = userId;
        this.episodeId = episodeId;
        this.createdAt = createdAt;
        this.offset = offset;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
