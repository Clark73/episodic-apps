package com.example.episodicshows.viewings.service;


import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.model.EpisodeModel;
import com.example.episodicshows.shows.model.ShowModel;
import com.example.episodicshows.shows.service.EpisodeService;
import com.example.episodicshows.shows.service.ShowsService;
import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.entity.ViewingsRepository;
import com.example.episodicshows.viewings.model.RecentViewing;
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

    public ViewingsService(EpisodeService episodeService, ShowsService showsService, ViewingsRepository viewingsRepository) {
        this.episodeService = episodeService;
        this.showsService = showsService;
        this.viewingsRepository = viewingsRepository;
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

    public void updateOrCreateViewing (Viewing viewing) {
        viewing.setShowId(showsService.findShowIdByEpisodeId(viewing.getEpisodeId()));
        Viewing existingView = this.viewingsRepository.findByUserIdAndShowId(viewing.getUserId(), viewing.getShowId());

        if (new CanCreateNewViewing().isSatisfiedBy(viewing, viewingsRepository)) {
            existingView.setEpisodeId(viewing.getEpisodeId());
            existingView.setUpdatedAt(viewing.getUpdatedAt());
            existingView.setTimeCode(viewing.getTimeCode());
            this.viewingsRepository.save(existingView);
        } else {
            this.viewingsRepository.save(viewing);
        }
    }




}
