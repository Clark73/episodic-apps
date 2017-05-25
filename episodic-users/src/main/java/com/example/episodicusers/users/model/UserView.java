package com.example.episodicusers.users.model;



import com.example.episodicusers.model.GenericModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserView extends GenericModel {

    @JsonProperty("id")
    private final long entityId;
    private final String email;

    public UserView(long entityId, String email) {
        this.entityId = entityId;
        this.email = email;
    }

    public long getEntityId() {
        return entityId;
    }

    public String getEmail() {
        return email;
    }
}
