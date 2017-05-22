package com.example.episodicshows.shows.entity;


import com.example.episodicshows.model.GenericRepository;

import java.util.List;
import java.util.stream.Stream;


public interface EpisodesRepository extends GenericRepository<Episode, Long> {

    List<Episode> findAllByShowId(long showId);

    default Stream<Episode> findAllByShowIdAsStream(long showId ) {
        return findAllByShowId(showId).stream();
    }

}
