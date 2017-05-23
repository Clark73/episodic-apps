package com.example.episodicshows.viewings;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.model.GenericModel;
import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.model.RecentViewings;
import com.example.episodicshows.viewings.service.ViewingsService;
import com.sun.media.sound.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/{userId}")
public class ViewingsController {

    private final ViewingsService viewingsService;

    public ViewingsController(ViewingsService viewingsService) {
        this.viewingsService = viewingsService;
    }

    @PatchMapping("/viewings")
    public ResponseEntity<GenericModel> updateOrCreateViewingStatus(@PathVariable long userId, @RequestBody Viewing viewing) {
        viewing.setUserId(userId);
        GenericModel gm = new GenericModel();
        gm.add(linkTo(methodOn(this.getClass()).updateOrCreateViewingStatus(userId, new Viewing())).withSelfRel());

        try {
            viewingsService.updateOrCreateViewing(viewing);
        } catch (InvalidDataException e) {
            e.printStackTrace();
            return new ResponseEntity<>(gm, HttpStatus.BAD_REQUEST);
        }

        gm.add(linkTo(methodOn(this.getClass()).getRecentlyViewed(userId)).withRel("recently_viewed"));

        return new ResponseEntity<>(gm, HttpStatus.OK);
    }

    @GetMapping("/recently-watched")
    @Transactional(readOnly = true)
    public ResponseEntity<RecentViewings> getRecentlyViewed (@PathVariable long userId) {
        RecentViewings recentViewings = new RecentViewings(viewingsService.getRecentViewings(userId));
        recentViewings.add(linkTo(methodOn(this.getClass()).getRecentlyViewed(userId)).withSelfRel());

        return new ResponseEntity<>(recentViewings, HttpStatus.OK);
    }

}
