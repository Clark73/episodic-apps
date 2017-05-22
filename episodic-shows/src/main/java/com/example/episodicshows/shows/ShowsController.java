package com.example.episodicshows.shows;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.entity.ShowsRepository;
import com.example.episodicshows.shows.model.ShowModel;
import com.example.episodicshows.shows.model.ShowView;
import com.example.episodicshows.shows.service.ShowsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowsController {


    private final ShowsService showsService;

    public ShowsController(ShowsService showsService) {
        this.showsService = showsService;
    }

    @GetMapping("")
    public ResponseEntity<List<ShowModel>> getShows () {
        return new ResponseEntity<>(showsService.findAllShows(), HttpStatus.OK);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowView> getShow ( @PathVariable long showId ) {
        ShowView showView = showsService.findShow(showId);
        showView.add(linkTo(methodOn(this.getClass()).getShow(showId)).withSelfRel());
        showView.add(linkTo(methodOn(EpisodesController.class).createEpisode(showId, new Episode())).withRel("add_episode"));

        return new ResponseEntity<>(showView, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ShowModel> createShow (@RequestBody Show show) {
        ShowModel sm = showsService.createShow(show);
        sm.add(linkTo(methodOn(this.getClass()).getShow(sm.getEntityId())).withRel("get_show"));
        sm.add(linkTo(methodOn(this.getClass()).createShow(new Show())).withSelfRel());
        return new ResponseEntity<>(sm, HttpStatus.OK);
    }


}
