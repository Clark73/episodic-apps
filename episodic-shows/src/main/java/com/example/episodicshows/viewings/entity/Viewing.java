package com.example.episodicshows.viewings.entity;

import com.example.episodicshows.model.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity( name = "viewings")
public class Viewing extends GenericEntity {

    @JsonIgnore
    private long userId;

    @JsonIgnore
    private long showId;

    private long episodeId;
    private LocalDateTime updatedAt;
    private int timeCode;

    public Viewing() {
    }

    public Viewing(long userId, long episodeId, LocalDateTime updatedAt, int timeCode) {
        this.userId = userId;
        this.episodeId = episodeId;
        this.updatedAt = updatedAt;
        this.timeCode = timeCode;
    }

    public Viewing(long userId, long showId, long episodeId, LocalDateTime updatedAt, int timeCode) {
        this.userId = userId;
        this.showId = showId;
        this.episodeId = episodeId;
        this.updatedAt = updatedAt;
        this.timeCode = timeCode;
    }



    public long getUserId() {
        return userId;
    }

    public long getShowId() {
        return showId;
    }

    @JsonIgnore
    public long getEpisodeId() {
        return episodeId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getTimeCode() {
        return timeCode;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    @JsonProperty
    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTimeCode(int timeCode) {
        this.timeCode = timeCode;
    }

    @Override
    @JsonIgnore
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }
}
