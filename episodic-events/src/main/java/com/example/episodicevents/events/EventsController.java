package com.example.episodicevents.events;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicevents.events.entity.EventsRepository;
import com.example.episodicevents.events.entity.Event;
import com.example.episodicevents.events.model.RecentEvents;
import com.example.episodicevents.generic.GenericModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventsController {

    private final EventsRepository eventsRepository;
    private final EventsService eventsService;

    public EventsController(EventsRepository eventsRepository, EventsService eventsService) {
        this.eventsRepository = eventsRepository;
        this.eventsService = eventsService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericModel> createEvent(@RequestBody Event event) {
        eventsService.saveEvent(event);


        GenericModel gm = new GenericModel();
        gm.add(linkTo(methodOn(this.getClass()).createEvent(new Event())).withSelfRel());
        gm.add(linkTo(methodOn(this.getClass()).getRecentEvents(0, 20)).withRel("recent_events"));
        return new ResponseEntity<>(gm, HttpStatus.OK);
    }

    @GetMapping("/recent")
    @Transactional(readOnly = true)
    public ResponseEntity<RecentEvents> getRecentEvents(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        Pageable pagerequest = new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "createdAt"));
        Page<Event> pagedData = eventsRepository.findAll(pagerequest);

        RecentEvents re = new RecentEvents(pagedData.getContent());
        re.add(linkTo(methodOn(this.getClass()).getRecentEvents(page, pageSize)).withSelfRel());

        if (page + 1 < pagedData.getTotalPages()) {
            re.add(linkTo(methodOn(this.getClass()).getRecentEvents(page + 1, pageSize)).withRel("next"));
        }
        if (page > 1) {
            re.add(linkTo(methodOn(this.getClass()).getRecentEvents(page - 1, pageSize)).withRel("previous"));
        }
        if (page != 0) {
            re.add(linkTo(methodOn(this.getClass()).getRecentEvents(0, pageSize)).withRel("first"));
        }
        if (page + 1 != pagedData.getTotalPages()) {
            re.add(linkTo(methodOn(this.getClass()).getRecentEvents(pagedData.getTotalPages(), pageSize)).withRel("last"));
        }

        return new ResponseEntity<>(re, HttpStatus.OK);
    }

}
