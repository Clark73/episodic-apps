package com.example.episodicshows.shows.entity;

import com.example.episodicshows.model.GenericEntity;

import javax.persistence.Entity;

@Entity(name = "shows")
public class Show extends GenericEntity {

    private String name;

    public Show() {
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                '}';
    }

    public Show(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
