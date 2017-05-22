package com.example.episodicshows.shows.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.shows.EpisodesController;
import com.example.episodicshows.shows.ShowsController;
import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.entity.ShowsRepository;
import com.example.episodicshows.shows.model.EpisodeModel;
import com.example.episodicshows.shows.model.ShowModel;
import com.example.episodicshows.shows.model.ShowView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowsService {

    private final ShowsRepository showsRepository;
    private final EpisodeService episodeService;
    private final EpisodesRepository episodesRepository;

    public ShowsService(ShowsRepository showsRepository, EpisodeService episodeService, EpisodesRepository episodesRepository) {
        this.showsRepository = showsRepository;
        this.episodeService = episodeService;
        this.episodesRepository = episodesRepository;

        assert showsRepository != null;
        assert episodeService != null;
        assert episodesRepository != null;
    }

    @Transactional(readOnly = true )
    public ShowView findShow( long id ) {
        Show show = showsRepository.findOne(id);
        return new ShowView(show, episodeService.getEpisodesByShowId(id));
    }


    @Transactional(readOnly = true)
    public List<ShowModel> findAllShows () {
        return showsRepository.findAllAsStream()
                .map(s -> new ShowModel(s.getId(), s.getName()))
                .map(s -> {
                    s.add(linkTo(methodOn(ShowsController.class).getShow(s.getEntityId())).withRel("get_show"));
                    s.add(linkTo(methodOn(EpisodesController.class).createEpisode(s.getEntityId(), new Episode())).withRel("add_episode"));
                    return s;
                })
                .collect(Collectors.toList());
    }

    public ShowModel createShow (Show show) {
        Show entityShow = showsRepository.save(show);
        return new ShowModel(entityShow.getId(), entityShow.getName());
    }

    @Transactional(readOnly = true)
    public Map<Long, ShowModel> findAllShowsAsMap () {
        return showsRepository.findAllAsStream()
                .map(s -> new ShowModel(s.getId(), s.getName()))
                .collect(Collectors.toMap(
                        ShowModel::getEntityId,
                        Function.identity()
                ));
    }

    @Transactional(readOnly = true)
    public long findShowIdByEpisodeId (long id) {
        Episode episode = episodesRepository.findOne(id);
        return episode.getShowId();
    }





}
