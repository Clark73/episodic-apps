package com.example.episodicshows.viewings.model;


import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.Show;

import java.time.LocalDateTime;


public class RecentViewing {
    private final Show show;
    private final Episode episode;
    private final LocalDateTime updatedAt;
    private final int timeCode;

    public RecentViewing(Show show, Episode episode, LocalDateTime updatedAt, int timeCode) {
        this.show = show;
        this.episode = episode;
        this.updatedAt = updatedAt;
        this.timeCode = timeCode;
    }

    public Show getShow() {
        return show;
    }

    public Episode getEpisode() {
        return episode;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getTimeCode() {
        return timeCode;
    }
}
