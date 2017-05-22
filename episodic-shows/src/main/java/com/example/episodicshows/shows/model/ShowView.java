package com.example.episodicshows.shows.model;


import com.example.episodicshows.model.GenericModel;
import com.example.episodicshows.shows.entity.Show;

import java.util.List;

public class ShowView extends GenericModel {

    private final Show show;
    private final List<EpisodeModel> episodes;

    public ShowView(Show show, List<EpisodeModel> episodes) {
        this.show = show;
        this.episodes = episodes;
    }

    public Show getShow() {
        return show;
    }

    public List<EpisodeModel> getEpisodes() {
        return episodes;
    }
}
