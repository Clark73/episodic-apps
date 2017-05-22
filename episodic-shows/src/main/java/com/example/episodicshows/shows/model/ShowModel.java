package com.example.episodicshows.shows.model;


import com.example.episodicshows.model.GenericModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowModel extends GenericModel {
    @JsonProperty("id")
    private final long entityId;
    private final String name;

    public ShowModel(long entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    public long getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }
}
