package com.example.episodicshows.shows.model;


import com.example.episodicshows.model.GenericModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EpisodeModel extends GenericModel {

    @JsonProperty("id")
    private final long entityId;

    private final int seasonNumber;
    private final int episodeNumber;
    private final String title;

    public EpisodeModel(long entityId, int seasonNumber, int episodeNumber) {
        this.entityId = entityId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.title = String.format("S%d E%d", seasonNumber, episodeNumber);
    }

    public long getEntityId() {
        return entityId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getTitle() {
        return title;
    }
}
