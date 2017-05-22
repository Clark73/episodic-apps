package com.example.episodicshows.shows;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.model.EpisodeModel;
import com.example.episodicshows.shows.service.EpisodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows/{showId}/episodes")
public class EpisodesController {

    private final EpisodeService episodeService;


    public EpisodesController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping("")
    public ResponseEntity<EpisodeModel> createEpisode(@PathVariable long showId, @RequestBody Episode episode){
        episode.setShowId(showId);
        EpisodeModel em = episodeService.createEpisode(episode);
        em.add(linkTo(methodOn(this.getClass()).createEpisode(showId, new Episode())).withSelfRel());
        em.add(linkTo(methodOn(ShowsController.class).getShow(showId)).withRel("get_show"));
        return new ResponseEntity<>(em, HttpStatus.OK);
    }

//    @GetMapping("")
//    @Transactional(readOnly = true)
//    public List<Episode> getEpisodesByShowId( @PathVariable long showId ) {
//        return episodesRepository.findAllByShowId(showId);
//    }

}
