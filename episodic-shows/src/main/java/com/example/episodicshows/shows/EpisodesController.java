package com.example.episodicshows.shows;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.entity.ShowViews;
import com.example.episodicshows.shows.entity.ShowsRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows/{showId}/episodes")
public class EpisodesController {

    private final EpisodesRepository episodesRepository;

    public EpisodesController(EpisodesRepository episodesRepository) {
        this.episodesRepository = episodesRepository;
    }

    @PostMapping("")
    public Episode createEpisode(@PathVariable long showId, @RequestBody Episode episode){
        episode.setShowId(showId);
        return episodesRepository.save(episode);
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public List<Episode> getEpisodesById( @PathVariable long showId ) {
        return episodesRepository.findAllByShowId(showId);
    }

}
