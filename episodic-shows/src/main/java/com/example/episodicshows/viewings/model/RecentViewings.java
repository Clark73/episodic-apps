package com.example.episodicshows.viewings.model;


import com.example.episodicshows.model.GenericModel;

import java.util.List;

public class RecentViewings extends GenericModel {
    private final List<RecentViewing> recentViewings;

    public RecentViewings(List<RecentViewing> recentViewings) {
        this.recentViewings = recentViewings;
    }

    public List<RecentViewing> getRecentViewings() {
        return recentViewings;
    }
}
