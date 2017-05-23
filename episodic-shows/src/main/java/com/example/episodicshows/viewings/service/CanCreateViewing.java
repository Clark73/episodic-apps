package com.example.episodicshows.viewings.service;



import com.example.episodicshows.shows.model.ShowView;
import com.example.episodicshows.shows.service.ShowsService;
import com.example.episodicshows.users.model.UserView;
import com.example.episodicshows.users.service.UsersService;
import com.example.episodicshows.viewings.entity.Viewing;


public class CanCreateViewing {

    public boolean isSatisfiedBy(Viewing viewing, UsersService usersService, ShowsService showService) {
        UserView uv = usersService.getUserById(viewing.getUserId());
        ShowView sv = showService.findShow(viewing.getShowId());

        return uv != null && sv != null;
    }
}
