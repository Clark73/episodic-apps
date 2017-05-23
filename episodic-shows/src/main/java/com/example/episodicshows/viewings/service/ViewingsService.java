package com.example.episodicshows.viewings.service;


import com.example.episodicshows.shows.model.EpisodeModel;
import com.example.episodicshows.shows.model.ShowModel;
import com.example.episodicshows.shows.service.EpisodeService;
import com.example.episodicshows.shows.service.ShowsService;
import com.example.episodicshows.users.service.UsersService;
import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.entity.ViewingsRepository;
import com.example.episodicshows.viewings.model.RecentViewing;
import com.sun.media.sound.InvalidDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ViewingsService {

    private final EpisodeService episodeService;
    private final ShowsService showsService;
    private final ViewingsRepository viewingsRepository;
    private final UsersService usersService;

    public ViewingsService(EpisodeService episodeService, ShowsService showsService, ViewingsRepository viewingsRepository, UsersService usersService) {
        this.episodeService = episodeService;
        this.showsService = showsService;
        this.viewingsRepository = viewingsRepository;
        this.usersService = usersService;
    }

    @Transactional(readOnly = true)
    public List<RecentViewing> getRecentViewings (long userId) {
        Map<Long, EpisodeModel> episodes = episodeService.findAllEpisodesAsMap();
        Map<Long, ShowModel> shows = showsService.findAllShowsAsMap();

        return viewingsRepository.findAllByUserIdAsStream(userId)
                .map(v -> new RecentViewing(
                        shows.get(v.getShowId()),
                        episodes.get(v.getEpisodeId()),
                        v.getUpdatedAt(),
                        v.getTimeCode()))
                .collect(Collectors.toList());
    }

    public void updateOrCreateViewing (Viewing viewing) throws InvalidDataException {
        viewing.setShowId(showsService.findShowIdByEpisodeId(viewing.getEpisodeId()));
        Viewing existingView = this.viewingsRepository.findByUserIdAndShowId(viewing.getUserId(), viewing.getShowId());

        if (new CanUpdateViewing().isSatisfiedBy(viewing, viewingsRepository)) {
            existingView.setEpisodeId(viewing.getEpisodeId());
            existingView.setUpdatedAt(viewing.getUpdatedAt());
            existingView.setTimeCode(viewing.getTimeCode());
            this.viewingsRepository.save(existingView);
        } else if (new CanCreateViewing().isSatisfiedBy(viewing, usersService, showsService)) {
            this.viewingsRepository.save(viewing);
        } else {
            throw new InvalidDataException(String.format("Bad Viewing Request: %s", viewing.toString()));
        }
    }




}
