package com.example.episodicshows.viewings.model;


import com.example.episodicshows.model.GenericModel;
import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.model.EpisodeModel;
import com.example.episodicshows.shows.model.ShowModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;


public class RecentViewing extends GenericModel {

    private final ShowModel show;
    private final EpisodeModel episode;
    private final LocalDateTime updatedAt;
    private final int timeCode;

    public RecentViewing(ShowModel show, EpisodeModel episode, LocalDateTime updatedAt, int timeCode) {
        this.show = show;
        this.episode = episode;
        this.updatedAt = updatedAt;
        this.timeCode = timeCode;
    }

    public ShowModel getShow() {
        return show;
    }

    public EpisodeModel getEpisode() {
        return episode;
    }

    public String getUpdatedAt() {
        return updatedAt.toString();
    }

    public int getTimeCode() {
        return timeCode;
    }
}
