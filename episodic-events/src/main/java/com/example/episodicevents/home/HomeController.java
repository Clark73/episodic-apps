package com.example.episodicevents.home;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicevents.events.EventsController;
import com.example.episodicevents.events.entity.Event;
import com.example.episodicevents.generic.GenericModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<GenericModel> options ( ) {
        GenericModel gm = new GenericModel();

        gm.add(linkTo(methodOn(EventsController.class).createEvent(new Event())).withRel("create_event"));
        gm.add(linkTo(methodOn(EventsController.class).getRecentEvents(0, 20)).withRel("recent_events"));

        return new ResponseEntity<>(gm, HttpStatus.OK);
    }


}
