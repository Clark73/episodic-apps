package com.example.episodicshows.users.entity;

import com.example.episodicshows.model.GenericEntity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends GenericEntity{

    private String email;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
