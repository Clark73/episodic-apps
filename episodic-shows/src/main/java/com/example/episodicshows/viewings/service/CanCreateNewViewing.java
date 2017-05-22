package com.example.episodicshows.viewings.service;


import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.entity.ViewingsRepository;

public class CanCreateNewViewing {
    public boolean isSatisfiedBy(Viewing viewing, ViewingsRepository viewingsRepository) {
        Viewing existingViewing = viewingsRepository.findByUserIdAndShowId(viewing.getUserId(), viewing.getShowId());

        return existingViewing != null;
    }
}
