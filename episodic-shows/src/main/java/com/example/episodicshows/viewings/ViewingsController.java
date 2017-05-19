package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.entity.ShowsRepository;
import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.entity.ViewingsRepository;
import com.example.episodicshows.viewings.model.RecentViewing;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}")
public class ViewingsController {

    private ViewingsRepository viewingsRepository;
    private EpisodesRepository episodesRepository;
    private ShowsRepository showsRepository;

    public ViewingsController(ViewingsRepository viewingsRepository, EpisodesRepository episodesRepository, ShowsRepository showsRepository) {
        this.viewingsRepository = viewingsRepository;
        this.episodesRepository = episodesRepository;
        this.showsRepository = showsRepository;
    }

    @PatchMapping("/viewings")
    public void updateOrCreateViewingStatus(@PathVariable long userId, @RequestBody Viewing viewing) {
        viewing.setShowId(episodesRepository.findOne(viewing.getEpisodeId()).getShowId());
        Viewing existingView = this.viewingsRepository.findByUserIdAndShowId(userId, viewing.getShowId());

        if (existingView != null) {
            existingView.setEpisodeId(viewing.getEpisodeId());
            existingView.setUpdatedAt(viewing.getUpdatedAt());
            existingView.setTimeCode(viewing.getTimeCode());
            this.viewingsRepository.save(existingView);
        } else {
            viewing.setUserId(userId);
            this.viewingsRepository.save(viewing);
        }
    }

    @GetMapping("/recently-watched")
    @Transactional(readOnly = true)
    public List<RecentViewing> getRecentlyViewed (@PathVariable long userId) {

        Map<Long, Episode> episodes = episodesRepository.findAllAsMap();
        Map<Long, Show> shows = showsRepository.findAllAsMap();

        return viewingsRepository.findAllByUserIdAsStream(userId)
                .map(v -> new RecentViewing(
                        shows.get(v.getShowId()),
                        episodes.get(v.getEpisodeId()),
                        v.getUpdatedAt(),
                        v.getTimeCode()))
                .collect(Collectors.toList());
    }

}
