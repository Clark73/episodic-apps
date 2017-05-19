package com.example.episodicshows.shows;

import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.entity.ShowsRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shows")
public class ShowsController {

    private final ShowsRepository showsRepository;

    public ShowsController(ShowsRepository showsRepository) {
        this.showsRepository = showsRepository;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public List<Show> getShows () {
        return showsRepository.findAll();
    }

    @PostMapping("")
    public Show createShow (@RequestBody Show show ) {
        return showsRepository.save(show);
    }


}
