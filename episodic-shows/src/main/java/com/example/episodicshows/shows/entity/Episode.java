package com.example.episodicshows.shows.entity;

import com.example.episodicshows.model.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity( name = "episodes" )
public class Episode extends GenericEntity {

    private int seasonNumber;

    private int episodeNumber;

    @JsonIgnore()
    private long showId;

    public Episode() {
    }

    public Episode(int seasonNumber, int episodeNumber, long showId) {
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.showId = showId;
    }

    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }


}
