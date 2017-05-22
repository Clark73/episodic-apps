package com.example.episodicshows.shows.service;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.model.EpisodeModel;
import com.example.episodicshows.shows.model.ShowModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EpisodeService {

    private final EpisodesRepository episodesRepository;

    public EpisodeService(EpisodesRepository episodesRepository) {
        this.episodesRepository = episodesRepository;
        assert episodesRepository != null;
    }

    public EpisodeModel createEpisode (Episode episode) {
        episode = episodesRepository.save(episode);
        return new EpisodeModel(episode.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
    }

    @Transactional(readOnly = true)
    public List<EpisodeModel> getEpisodesByShowId (long showId) {
        return episodesRepository.findAllByShowIdAsStream(showId)
                .map(e -> new EpisodeModel(
                        e.getId(),
                        e.getSeasonNumber(),
                        e.getEpisodeNumber()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<Long, EpisodeModel> findAllEpisodesAsMap () {
        return episodesRepository.findAllAsStream()
                .map(s -> new EpisodeModel(s.getId(), s.getSeasonNumber(), s.getEpisodeNumber()))
                .collect(Collectors.toMap(
                        EpisodeModel::getEntityId,
                        Function.identity()
                ));
    }


}
