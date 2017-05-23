package com.example.episodicevents.events.model;


import com.example.episodicevents.events.entity.Event;
import com.example.episodicevents.generic.GenericModel;

import java.util.List;

public class RecentEvents extends GenericModel {
    private final List<Event> events;

    public RecentEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
