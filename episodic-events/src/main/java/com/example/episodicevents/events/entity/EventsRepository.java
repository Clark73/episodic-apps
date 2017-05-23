package com.example.episodicevents.events.entity;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface EventsRepository extends MongoRepository<Event, String>{
    List<Event> findAll();

    Page<Event> findAll(Pageable pageable);

}
