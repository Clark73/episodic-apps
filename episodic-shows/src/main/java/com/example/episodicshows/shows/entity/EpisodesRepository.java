package com.example.episodicshows.shows.entity;


import com.example.episodicshows.model.GenericRepository;

import java.util.List;


public interface EpisodesRepository extends GenericRepository<Episode, Long> {

    List<Episode> findAllByShowId(long showId);

}
