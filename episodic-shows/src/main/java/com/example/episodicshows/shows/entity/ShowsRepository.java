package com.example.episodicshows.shows.entity;



import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ShowsRepository extends CrudRepository<Show, Long> {
    List<Show> findAll();

    default Stream<Show> findAllAsStream() {
        return findAll().stream();
    }
}
